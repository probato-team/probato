package org.probato.validator;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

import org.probato.entity.type.ComponentValidatorType;
import org.probato.exception.IntegrityException;

public abstract class ComponentValidator {

	private static final String COMPONENT_VALIDATOR_IMPLEMENTATION_NOT_FOUND = "Component validator implementation not found";
	
	private ComponentValidator next;
	
	public abstract ComponentValidatorType getStrategy();
	
	public abstract boolean accepted(ComponentValidatorType type);
	
	public abstract void execute(Class<?> suiteClazz);

	protected void chain(Class<?> suiteClazz) {
		if (Objects.nonNull(next)) {
			next.execute(suiteClazz);
		}
	}
	
	public static List<ComponentValidator> getInstance(ComponentValidatorType type) {
		return  ServiceLoader.load(ComponentValidator.class).stream()
			.map(Provider::get)
			.sorted(Comparator.comparing(serviceClazz -> serviceClazz.getClass().getPackageName().equals(ComponentValidator.class.getClass().getPackageName()), Comparator.reverseOrder()))
			.filter(component -> component.accepted(type))
			.collect(Collectors.toList());
	}
	
	public static ComponentValidator link(List<ComponentValidator> validators) {
		
		if (validators.isEmpty()) {
			throw new IntegrityException(COMPONENT_VALIDATOR_IMPLEMENTATION_NOT_FOUND);
		}
		
		var first = validators.remove(0);
		var head = first;
		for (var item : validators) {
			head.next = item;
			head = item;
		}
		
		return first;
	}

}