package org.probato.service;

import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

import org.probato.database.NoSqlProvider;

public class NoSqlService {

	private NoSqlProvider provider;

	private NoSqlService() {
		load();
	}

	public static NoSqlService get() {
		return new NoSqlService();
	}

	public void run(Class<?> clazz) {
		Optional.ofNullable(provider)
			.ifPresent(prvd -> prvd.run(clazz));
	}

	private void load() {
		if (Objects.isNull(provider)) {
			provider = ServiceLoader.load(NoSqlProvider.class)
					.stream()
					.map(Provider::get)
					.findFirst()
					.orElse(provider);
		}
	}

}