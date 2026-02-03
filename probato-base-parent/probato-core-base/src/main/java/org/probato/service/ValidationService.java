package org.probato.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.probato.type.ComponentValidatorType;
import org.probato.validator.ComponentValidator;

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
			.collect(Collectors.toList());

		validator = ComponentValidator.link(validators);
	}

	public static  ValidationService get() {
		return new ValidationService();
	}

	public void execute(Class<?> suiteClazz) {
		validator.execute(suiteClazz);
	}

}