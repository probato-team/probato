package org.probato.validator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.probato.model.type.ComponentValidatorType;

public class ValidationService {
	
	private ComponentValidator validator;
	
	private ValidationService() {
		
		var validators = Stream.of(
				ComponentValidator.getInstance(ComponentValidatorType.CONFIGURATION),
				ComponentValidator.getInstance(ComponentValidatorType.SUITE),
				ComponentValidator.getInstance(ComponentValidatorType.SCRIPT),
				ComponentValidator.getInstance(ComponentValidatorType.PROCEDURE),
				ComponentValidator.getInstance(ComponentValidatorType.PAGE_OBJECT),
				ComponentValidator.getInstance(ComponentValidatorType.DATASET),
				ComponentValidator.getInstance(ComponentValidatorType.DATASOURCE),
				ComponentValidator.getInstance(ComponentValidatorType.BROWSER)
			)
			.flatMap(List::stream)
			.collect(Collectors.toList()); // NOSONAR It needs to be a modifiable list
		
		validator = ComponentValidator.link(validators);
	}
	
	public void execute(Class<?> suiteClazz) {
		validator.execute(suiteClazz);
	}
	
	public static  ValidationService getInstance() {
		return new ValidationService();
	}

}