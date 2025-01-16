package org.probato.validator;

import java.util.Objects;

import org.probato.exception.IntegrityException;
import org.probato.loader.Configuration;
import org.probato.model.Execution;
import org.probato.model.type.ComponentValidatorType;
import org.probato.util.StringUtil;

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
		
		var configuration = Configuration.getInstance(suiteClazz);
		
		validateExecution(configuration);

		chain(suiteClazz);
	}

	private void validateExecution(Configuration configuration) {
		
		var execution = configuration.getExecution();
		if (Objects.isNull(execution)) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution");
		}
		
		validateManager(execution);
	}
	
	private void validateManager(Execution execution) {
		
		var manager = execution.getManager();
		
		if (Objects.isNull(manager)) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.manager");
		}
		
		if (manager.isSubmit()) {

			if (StringUtil.isBlank(manager.getUrl())) {
				throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.manager.url");
			}

			if (StringUtil.isBlank(manager.getToken())) {
				throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.manager.token");
			}
		}
	}

}