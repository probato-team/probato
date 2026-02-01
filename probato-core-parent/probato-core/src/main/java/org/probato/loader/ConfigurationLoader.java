package org.probato.loader;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.probato.exception.IntegrityException;
import org.probato.model.Configuration;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class ConfigurationLoader {

	private static final String SYS_PROP_PREFIX = "probato.";
	private static final String BASE_FILE = "configuration.yml";
	private static final String PROFILE_FILE = "configuration-{0}.yml";
	private static final String NOT_FOUND = "configuration{0}.yml file not found";

	public Configuration load(Class<?> source, List<String> profiles) {

		var merged = new HashMap<String, Object>();
		var classLoader = resolveClassLoader(source);
		var base = classLoader.getResourceAsStream(BASE_FILE);
		if (Objects.isNull(base)) {
			throw new IntegrityException(NOT_FOUND, "");
		}

		deepMerge(merged, loadAsMap(base));

		for (var profile : profiles) {

			var file = MessageFormat.format(PROFILE_FILE, profile);
			var is = classLoader.getResourceAsStream(file);
			if (Objects.isNull(is)) {
				throw new IntegrityException(NOT_FOUND, "-" + profile);
			}

			deepMerge(merged, loadAsMap(is));
		}

		deepMerge(merged, loadSystemOverrides());

		var config = bind(merged);
		config.initDefaults();

		return config;
	}

	private Map<String, Object> loadSystemOverrides() {

		var overrides = new HashMap<String, Object>();
		System.getProperties()
			.forEach((k, v) -> {

				var key = k.toString();
				if (!key.startsWith(SYS_PROP_PREFIX)) {
					return;
				}

				var normalized = key.substring(SYS_PROP_PREFIX.length());
				var path = normalized.split("\\.");

				applyPath(overrides, path, v.toString());
			});

		return overrides;
	}

	@SuppressWarnings("unchecked")
	private void applyPath(Map<String, Object> root, String[] path, Object value) {

		var current = root;
		for (int i = 0; i < path.length - 1; i++) {

			var key = path[i];
			var next = current.get(key);
			if (!(next instanceof Map)) {
				next = new HashMap<String, Object>();
				current.put(key, next);
			}
			current = (Map<String, Object>) next;
		}

		current.put(path[path.length - 1], cast(value));
	}

	private Object cast(Object value) {

		if (!(value instanceof String)) {
			return value;
		}

		var str = value.toString();
		if (str.matches("^-?\\d+$")) {
			return Integer.parseInt(str);
		}

		if (str.matches("^-?\\d+\\.\\d+$")) {
			return Double.parseDouble(str);
		}

		if ("true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str)) {
			return Boolean.parseBoolean(str);
		}

		return str;
	}

	public List<String> resolveProfiles() {

		var raw = System.getProperty("profile");
		if (Objects.isNull(raw) || raw.isBlank()) {
			raw = System.getenv("PROBATO_PROFILE");
		}

		if (Objects.isNull(raw) || raw.isBlank()) {
			return List.of();
		}

		var parts = raw.split(",");
		var profiles = new ArrayList<String>();

		for (var profile : parts) {
			if (!profile.isBlank()) {
				profiles.add(profile.trim());
			}
		}

		return profiles;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> loadAsMap(InputStream is) {

		var yaml = new Yaml();
		var data = yaml.load(is);

		return Objects.isNull(data)
				? new HashMap<>()
				: (Map<String, Object>) data;
	}

	@SuppressWarnings("unchecked")
	private void deepMerge(Map<String, Object> base, Map<String, Object> override) {

		for (Map.Entry<String, Object> entry : override.entrySet()) {

			var key = entry.getKey();
			var overrideValue = entry.getValue();

			if (base.containsKey(key)) {

				var baseValue = base.get(key);

				if (baseValue instanceof Map && overrideValue instanceof Map) {
					deepMerge((Map<String, Object>) baseValue, (Map<String, Object>) overrideValue);
				} else {
					base.put(key, overrideValue);
				}

			} else {
				base.put(key, overrideValue);
			}
		}
	}

	private Configuration bind(Map<String, Object> merged) {
		var yaml = new Yaml(new Constructor(Configuration.class, new LoaderOptions()));
		return yaml.loadAs(yaml.dump(merged), Configuration.class);
	}

	private ClassLoader resolveClassLoader(Class<?> source) {
		return Objects.nonNull(source)
				? source.getClassLoader()
				: Thread.currentThread().getContextClassLoader();
	}

}
