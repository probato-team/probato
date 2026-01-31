package org.probato.loader;

import java.util.List;
import java.util.Objects;

final class ConfigurationKey {

	private final List<String> profiles;

	private ConfigurationKey(List<String> profiles) {
		this.profiles = List.copyOf(profiles);
	}

	static ConfigurationKey of(List<String> profiles) {
		return new ConfigurationKey(profiles);
	}

	static ConfigurationKey defaultKey() {
		return new ConfigurationKey(List.of("default"));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ConfigurationKey))
			return false;
		var that = (ConfigurationKey) o;
		return profiles.equals(that.profiles);
	}

	@Override
	public int hashCode() {
		return Objects.hash(profiles);
	}

}