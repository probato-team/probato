package org.probato.model;

import java.util.Objects;

import org.probato.configuration.ConfigurationResolver;
import org.probato.exception.IntegrityException;
import org.probato.type.DimensionMode;

/**
 * Represents browser window dimension settings.
 *
 * <p>The dimension may operate in different modes, such as fullscreen,
 * maximized, or custom size.</p>
 *
 * <p>When the mode is {@link DimensionMode#CUSTOM}, both width and height
 * must be explicitly provided.</p>
 *
 * @throws IntegrityException if required configuration properties are missing or inconsistent
 */
public class Dimension {

	private Integer width;
	private Integer height;
	private DimensionMode mode;

	public Dimension() {

		var current = ConfigurationResolver.currentExecutionIndex();

		this.width = ConfigurationResolver
				.browserProperty(current, "dimension.width")
				.map(Integer::valueOf)
				.orElse(null);

		this.height = ConfigurationResolver
				.browserProperty(current, "dimension.height")
				.map(Integer::valueOf)
				.orElse(null);

		this.mode = ConfigurationResolver
				.browserProperty(current, "dimension.mode")
				.map(DimensionMode::fromString)
				.orElse(null);

		validate(current);
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

	public DimensionMode getMode() {
		return mode;
	}

	private void validate(int index) {
		validateMode(index);
		validateCustomMode(index);
	}

	private void validateMode(int index) {
		if (Objects.isNull(getMode())) {
			throw new IntegrityException("Property ''browsers[{0}].dimension.mode'' should be declared in ''configuration.yaml'' file", index);
		}
	}

	private void validateCustomMode(int index) {
		if (DimensionMode.CUSTOM.equals(getMode())
				&& (Objects.isNull(getHeight()) || Objects.isNull(getWidth()))) {
			throw new IntegrityException("Property ''browsers[{0}].dimension.height'' and ''browsers[{0}].dimension.width'' should be declared in ''configuration.yaml'' file when ''browsers[{0}].dimension.mode'' equals CUSTOM", index);
		}
	}

}