package org.probato.validator;

import java.util.Objects;

import org.probato.core.loader.Configuration;
import org.probato.entity.model.Execution;
import org.probato.entity.type.ComponentValidatorType;
import org.probato.exception.IntegrityException;
import org.probato.util.StringUtil;

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
		
		var configuration = Configuration.getInstance(suiteClazz);
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
		if (Objects.isNull(target) || StringUtil.isBlank(target.getUrl())) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.target.url");
		}

		if (StringUtil.isBlank(target.getVersion())) {
			throw new IntegrityException(PROPERTIE_NOT_FOUND_MSG, "execution.target.version");
		}
	}

}