package org.probato.loader;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.probato.model.Configuration;

public final class ConfigurationContext {

	private static final Map<ConfigurationKey, Configuration> CACHE = new ConcurrentHashMap<>();
	private static final ConfigurationLoader LOADER = new ConfigurationLoader();

	private ConfigurationContext() {}

	public static Configuration get(Class<?> source) {

		var profiles = LOADER.resolveProfiles();
		var key = profiles.isEmpty()
				? ConfigurationKey.defaultKey()
				: ConfigurationKey.of(profiles);

		return CACHE.computeIfAbsent(key, k -> LOADER.load(source, profiles));
	}

	public static Configuration get() {
		return get(null);
	}

	public static void clear() {
		CACHE.clear();
	}

	public static void clearProfiles(String... profiles) {
		CACHE.remove(ConfigurationKey.of(List.of(profiles)));
	}

}