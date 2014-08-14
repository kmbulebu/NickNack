package com.oakcity.nicknack.core.providers;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class ProviderClassloader extends URLClassLoader {

	public ProviderClassloader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
		super(urls, parent, factory);
	}

	public ProviderClassloader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	public ProviderClassloader(URL[] urls) {
		super(urls);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if (name.startsWith("com.oakcity.nicknack.core")) {
			return super.loadClass(name);
		} else {
			throw new ClassNotFoundException(name);
		}
	}
	
}
