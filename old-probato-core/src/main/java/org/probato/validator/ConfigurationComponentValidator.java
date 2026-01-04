package org.probato.validator;

import java.util.Objects;

import org.probato.exception.IntegrityException;
import org.probato.loader.Configuration;
import org.probato.model.Execution;
import org.probato.model.type.ComponentValidatorType;
import org.probato.util.StringUtil;

public class ConfigurationComponentValidator extends ComponentValidator {

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
		
		var configuration = Configuration.getInstance(suiteClazz);
		validateExecution(configuration);

		chain(suiteClazz);
	}

	private void validateExecution(Configuration configuration) {
		
		var execution = configuration.getExecution();
		if (Objects.isNull(execution)) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution");
		}
		
		if (Objects.isNull(execution.getScreen())) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.screen");
		}
		
		validateDelay(execution);
		validateDirectory(execution);
	}

	private void validateDelay(Execution execution) {
		var delay = execution.getDelay();
		if (Objects.isNull(delay) || Objects.isNull(delay.getActionInterval())) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.delay.actionInterval");
		}

		if (Objects.isNull(delay.getWaitingTimeout())) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.delay.waitingTimeout");
		}
	}
	
	private void validateDirectory(Execution execution) {
		var directory = execution.getDirectory();
		if (Objects.isNull(directory) || StringUtil.isBlank(directory.getTemp())) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.directory.temp");
		}
	}

}