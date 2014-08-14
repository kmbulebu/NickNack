package com.oakcity.nicknack.core.providers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

public class ProviderLoader {
	
	private final Path providersPath;
	
	private final ProviderClassloader parentClassLoader;
	
	public ProviderLoader(final Path providersPath) {
		this.providersPath = providersPath;
		if (!Files.isDirectory(providersPath)) {
			throw new IllegalArgumentException("providersPath must be a directory.");
		}
		this.parentClassLoader = new ProviderClassloader(new URL[]{}, this.getClass().getClassLoader());
	}
	
	public List<Provider> loadProviders() throws IOException {
		DirectoryStream<Path> dsStream;
		
		dsStream = Files.newDirectoryStream(providersPath, jarFileFilter);
		
		final List<Provider> providers = new LinkedList<Provider>();
		for (Path providerJar : dsStream) {
			providers.addAll(loadProvidersFromJar(providerJar));
		}
		return providers;
	}
	
	private List<Provider> loadProvidersFromJar(Path providerJar) throws MalformedURLException {
		//System.out.println(providerJar.toUri().toURL());
		final URLClassLoader classLoader = new URLClassLoader(new URL[]{providerJar.toUri().toURL()}, parentClassLoader);
		final ServiceLoader<Provider> loader = ServiceLoader.load(Provider.class, classLoader);
		
		final List<Provider> providers = new LinkedList<Provider>();
		for (Provider provider : loader) {
			providers.add(provider);
		}
		
		return providers;
	}
	
	final Filter<Path> jarFileFilter = new DirectoryStream.Filter<Path>() {

		@Override
		public boolean accept(Path entry) throws IOException {
			if (Files.isRegularFile(entry) && entry.toString().toLowerCase().endsWith(".jar")) {
				return true;
			} else {
				return false;
			}
		}
	};

}
