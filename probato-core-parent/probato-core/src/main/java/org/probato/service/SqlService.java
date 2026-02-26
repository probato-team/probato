package org.probato.service;

import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

import org.probato.database.SqlProvider;

public class SqlService {

	private SqlProvider provider;

	private SqlService() {
		load();
	}

	public static SqlService get() {
		return new SqlService();
	}

	public void run(Class<?> clazz) {
		Optional.ofNullable(provider)
			.ifPresent(prvd -> prvd.run(clazz));
	}

	private void load() {
		if (Objects.isNull(provider)) {
			provider = ServiceLoader.load(SqlProvider.class)
					.stream()
					.map(Provider::get)
					.findFirst()
					.orElse(provider);
		}
	}

}