package org.probato.model;

import java.util.Objects;

import org.probato.configuration.ConfigurationResolver;
import org.probato.exception.IntegrityException;
import org.probato.type.BrowserType;

/**
 * Represents a browser configuration used by Probato.
 *
 * <p>This class encapsulates the browser type, headless mode
 * and window dimension settings.</p>
 *
 * <p><strong>Note:</strong> The default constructor reads configuration
 * values from JVM system properties and validates them on instantiation.</p>
 *
 * @throws IntegrityException if required configuration properties are missing or invalid
 */
public class Browser {

	private BrowserType type;
	private boolean headless;
	private Dimension dimension;

	public Browser() {

		var current = ConfigurationResolver.currentExecutionIndex();

		this.type = ConfigurationResolver
				.browserProperty(current, "type")
				.map(BrowserType::fromString)
				.orElse(null);

		this.headless = ConfigurationResolver
				.browserProperty(current, "headless")
				.map(Boolean::valueOf)
				.orElse(Boolean.FALSE);

		this.dimension = new Dimension();

		validate(current);
	}

	public BrowserType getType() {
		return type;
	}

	public boolean isHeadless() {
		return headless;
	}

	public Dimension getDimension() {
		return dimension;
	}

	private void validate(int index) {
		validateType(index);
	}

	private void validateType(int index) {
		if (Objects.isNull(getType())) {
			throw new IntegrityException("Property ''browsers[{0}].type'' should be declared in ''configuration.yaml'' file", index);
		}
	}

}