package com.github.kmbulebu.nicknack.server.model;

import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.github.kmbulebu.nicknack.core.providers.Provider;

@Relation(value="Provider", collectionRelation="Providers")
public class ProviderResource extends ResourceSupport {

	private final Provider provider;
	
	public ProviderResource(Provider provider) {
		this.provider = provider;
	}

	public UUID getUuid() {
		return provider.getUuid();
	}

	public String getName() {
		return provider.getName();
	}

	public String getAuthor() {
		return provider.getAuthor();
	}

	public int getVersion() {
		return provider.getVersion();
	}

}
