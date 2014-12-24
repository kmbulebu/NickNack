package com.github.kmbulebu.nicknack.providers.pushover;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PushOverClientImpl implements PushOverClient {
	
	private String apiKey;
	
	private final RestTemplate restTemplate;
	private final ObjectMapper mapper;
	
	private static final String API_URL = "https://api.pushover.net/1/messages.json";
	
	public PushOverClientImpl() {
		final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new FormHttpMessageConverter());
		final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		mapper = jsonConverter.getObjectMapper();
		messageConverters.add(new MappingJackson2HttpMessageConverter());
		
		restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(messageConverters);
	}

	@Override
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public void sendPushMessage(PushMessage pushMessage) throws PushOverException, IOException {
		final MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		
		if (pushMessage.getMessage() == null) {
			throw new IllegalArgumentException("Message is required.");
		}
		
		if (pushMessage.getUser() == null) {
			throw new IllegalArgumentException("User is required.");
		}
		
		parameters.add("token", apiKey);
		
		parameters.add("user", pushMessage.getUser());
		parameters.add("message", pushMessage.getMessage());
		
		if (pushMessage.getDevice() != null) {
			parameters.add("device", pushMessage.getDevice());
		}
		
		if (pushMessage.getTitle() != null) {
			parameters.add("title", pushMessage.getTitle());
		}
		
		if (pushMessage.getPriority() != null) {
			parameters.add("priority", Integer.toString(pushMessage.getPriority()));
		}
		
		if (pushMessage.getTimeStamp() != null) {
			parameters.add("timestamp", Long.toString(pushMessage.getTimeStamp().getTime()/1000l));
		}
		
		if (pushMessage.getSound() != null) {
			parameters.add("sound", pushMessage.getSound());
		}
		
		if (pushMessage.getUrl() != null) {
			parameters.add("url", pushMessage.getUrl());
			
			if (pushMessage.getUrlTitle() != null) {
				parameters.add("url_title", pushMessage.getUrlTitle());
			}
		}
		
		PushOverResponse response = null;
		try {
			response = restTemplate.postForObject(API_URL, parameters, PushOverResponse.class);
		} catch (HttpClientErrorException e) {
			final String body = e.getResponseBodyAsString();
			if (body != null) {
				response = mapper.readValue(body, PushOverResponse.class);
				throw new PushOverException(response.getRequest(), response.getErrors(), response.getStatus(), e.getStatusCode().value());
			}
		} catch (RestClientException e) {
			throw new IOException("Could not connect to PushOver.", e);
		}
		
	}

}
