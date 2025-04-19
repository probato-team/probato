package org.probato.validator;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Array;
import java.util.Objects;

import org.probato.core.loader.Configuration;
import org.probato.entity.model.Browser;
import org.probato.entity.type.ComponentValidatorType;
import org.probato.entity.type.DimensionMode;
import org.probato.exception.IntegrityException;

public class BrowserComponentValidator extends ComponentValidator {

	private static final String SCREEN_NOT_FOUNT_MSG = "No screen found on the running machine. To run on a machine that does not have a screen, run with property ''browsers[{0}].headless'' with value 'false'";
	private static final String PROPERTIE_NOT_FOUND_MSG = "Property ''{0}'' should be declared in ''configuration.yaml'' file";
	private static final String PROPERTIE_BROWSER_NOT_DECLARED_MSG = "Property ''browsers[{0}].{1}'' should be declared in ''configuration.yaml'' file";
	private static final String PROPERTIE_BROWSER_DIMENSION_NOT_DECLARED_MSG = "Property ''browsers[{0}].dimension.height'' and ''browsers[{0}].dimension.width'' should be declared in ''configuration.yaml'' file when ''browsers[{0}].dimension.mode'' equals CUSTOM";

	@Override
	public ComponentValidatorType getStrategy() {
		return ComponentValidatorType.BROWSER;
	}
	
	@Override
	public boolean accepted(ComponentValidatorType type) {
		return getStrategy().equals(type);
	}

	@Override
	public void execute(Class<?> suiteClazz) {
		
		var configuration = Configuration.getInstance(suiteClazz);
		validateBrowsers(configuration);

		chain(suiteClazz);
	}

	private void validateBrowsers(Configuration configuration) {
		var browsers = configuration.getBrowsers();
		if (Objects.isNull(browsers) || Array.getLength(browsers) == 0) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "browsers");
		}

		for (int i = 0; i < browsers.length; i++) {
			validateBrowser(i, browsers[i]);
		}
	}

	private void validateBrowser(int index, Browser browser) {

		if (Objects.isNull(browser.getType())) {
			throw new IntegrityException(PROPERTIE_BROWSER_NOT_DECLARED_MSG, index, "type");
		}

		var dimension = browser.getDimension();
		if (Objects.isNull(dimension) || Objects.isNull(dimension.getMode())) {
			throw new IntegrityException(PROPERTIE_BROWSER_NOT_DECLARED_MSG, index, "dimension.mode");
		}

		if (DimensionMode.CUSTOM.equals(dimension.getMode())
				&& (Objects.isNull(dimension.getHeight()) || Objects.isNull(dimension.getWidth()))) {
			throw new IntegrityException(PROPERTIE_BROWSER_DIMENSION_NOT_DECLARED_MSG, index);
		}

		if (!browser.isHeadless()) {
			var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			var screens = ge.getScreenDevices();
			if (screens == null || Array.getLength(screens) == 0) {
				throw new IntegrityException(SCREEN_NOT_FOUNT_MSG, index);
			}
		}
	}

}