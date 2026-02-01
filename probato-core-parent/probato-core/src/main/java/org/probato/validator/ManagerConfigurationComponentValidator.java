package org.probato.validator;

import java.util.Objects;

import org.probato.exception.IntegrityException;
import org.probato.loader.ConfigurationContext;
import org.probato.model.Configuration;
import org.probato.model.Execution;
import org.probato.type.ComponentValidatorType;
import org.probato.utils.StringUtils;

public class ManagerConfigurationComponentValidator extends ComponentValidator {

	private static final String PROPERTIE_NOT_FOUND_MSG = "Property ''{0}'' should be declared in ''configuration.yml'' file";

	@Override
	public ComponentValidatorType getStrategy() {
		return ComponentValidatorType.CONFIGURATION;
	}

	@Override
	public boolean accepted(ComponentValidatorType type) {
		return getStrategy().equals(type);
	}

	@Override
	public void execute(Class<?> suiteClazz) {

		var configuration = ConfigurationContext.get(suiteClazz);

		validateExecution(configuration);

		chain(suiteClazz);
	}

	private void validateExecution(Configuration configuration) {
		var execution = configuration.getExecution();
		validateManager(execution);
	}

	private void validateManager(Execution execution) {

		var manager = execution.getManager();

		if (Objects.isNull(manager)) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.manager");
		}

		if (manager.isSubmit()) {

			if (StringUtils.isBlank(manager.getUrl())) {
				throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.manager.url");
			}

			if (StringUtils.isBlank(manager.getToken())) {
				throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.manager.token");
			}
		}
	}

}