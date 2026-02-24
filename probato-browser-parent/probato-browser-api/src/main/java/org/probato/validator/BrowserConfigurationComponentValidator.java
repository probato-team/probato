package org.probato.validator;

import java.util.Objects;

import org.probato.exception.IntegrityException;
import org.probato.loader.ConfigurationContext;
import org.probato.model.Configuration;
import org.probato.model.Execution;
import org.probato.type.ComponentValidatorType;

public class BrowserConfigurationComponentValidator extends ComponentValidator {

	private static final String PROPERTIE_NOT_FOUND_MSG = "Property ''{0}'' should be declared in ''configuration.yaml'' file";

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
		if (Objects.isNull(execution)) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution");
		}

		validateTarget(execution);
	}

	private void validateTarget(Execution execution) {

		var target = execution.getTarget();
		if (Objects.isNull(target) || Objects.isNull(target.getProjectId())) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.target.projectId");
		}

		if (Objects.isNull(target.getUrl()) || target.getUrl().isBlank()) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.target.url");
		}

		if (Objects.isNull(target.getVersion()) || target.getVersion().isBlank()) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.target.version");
		}
	}

}