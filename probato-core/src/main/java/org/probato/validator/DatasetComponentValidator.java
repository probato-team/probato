package org.probato.validator;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import org.probato.exception.IntegrityException;
import org.probato.loader.AnnotationLoader;
import org.probato.model.type.ComponentValidatorType;
import org.probato.util.FileUtil;
import org.probato.util.StringUtil;

public class DatasetComponentValidator extends ComponentValidator {
	
	private static final String DATASET_ANNOTATION_REQUIRED = "List of dataset files must have at least 1 item in the '@Dataset' annotation: ''{0}''";
	private static final String DATASET_PATH_REQUIRED = "Dataset path must be required in the '@Dataset' annotation: ''{0}''";
	private static final String DATASET_FILE_NOT_FOUNT = "Dataset file ''{0}'' not found: ''{1}''";

	@Override
	public ComponentValidatorType getStrategy() {
		return ComponentValidatorType.DATASET;
	}

	@Override
	public boolean accepted(ComponentValidatorType type) {
		return getStrategy().equals(type);
	}

	@Override
	public void execute(Class<?> suiteClazz) {
		
		boolean ignored = AnnotationLoader.isIgnore(suiteClazz);
		if (ignored) return;

		AnnotationLoader.getTestCaseField(suiteClazz).stream()
				.map(Field::getType)
				.forEach(this::validateScript);

		chain(suiteClazz);
	}

	private void validateScript(Class<?> scriptClazz) {
			
		boolean ignored = AnnotationLoader.isIgnore(scriptClazz);
		if (ignored) return;
		
		AnnotationLoader.getDataset(scriptClazz)
				.ifPresent(dataset -> {
					var value = dataset.value();
					validateValue(scriptClazz, dataset.value());
					Stream.of(value).forEach(path -> {
						validatePath(scriptClazz, path);
						validateFile(scriptClazz, path);
					});
				});
	}
	
	private void validateValue(Class<?> clazz, String [] value) {
		if (value.length == 0) {
			throw new IntegrityException(DATASET_ANNOTATION_REQUIRED, getName(clazz));
		}
	}

	private void validatePath(Class<?> clazz, String value) {
		if (StringUtil.isBlank(value)) {
			throw new IntegrityException(DATASET_PATH_REQUIRED, getName(clazz));
		}
	}
	
	private void validateFile(Class<?> clazz, String path) {
		if (!FileUtil.exists(path)) {
			throw new IntegrityException(DATASET_FILE_NOT_FOUNT, path, getName(clazz));
		}
	}
	
	private String getName(Class<?> scritpClazz) {
		return scritpClazz.getName();
	}

}