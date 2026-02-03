package org.probato;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.probato.api.Dataset;
import org.probato.api.Script;
import org.probato.loader.AnnotationLoader;
import org.probato.loader.ConfigurationContext;
import org.probato.loader.DatasetLoader;
import org.probato.model.Browser;
import org.probato.service.DatasetService;
import org.probato.service.ValidationService;
import org.probato.type.ExecutionPhase;

public class Probato {

	private static ValidationService validationService;
	private static DatasetService datasetService;
//	private static List<ExternalService> integrationsService;

	private Probato() {}

	public static void init(Class<?> clazz) {
		loadExtesions();
		validate(clazz);
		runIntegration(ExecutionPhase.BEFORE_ALL);
	}

	public static void setup() {
		runIntegration(ExecutionPhase.BEFORE_EACH);
	}

	public static void finish() {
		runIntegration(ExecutionPhase.AFTER_EACH);
	}

	public static void destroy() {
		runIntegration(ExecutionPhase.AFTER_ALL);
	}

	public static Stream<Class<?>> getTestsCase(Class<?> suiteClazz) {
		return AnnotationLoader.getTestsCase(suiteClazz);
	}

	public static Stream<Browser> loadBrowsers(Class<?> scriptClazz) {
		return Stream.of(ConfigurationContext.get(scriptClazz).getBrowsers());
	}

	public static Optional<Script> loadScript(Class<?> scriptClazz) {
		return AnnotationLoader.getScript(scriptClazz);
	}

	public static Optional<Dataset> loadDataset(Class<?> scriptClazz) {
		return DatasetLoader.getDataset(scriptClazz);
	}

	public static int getCsvCounterLines(Dataset dataset) {
		return datasetService.counterLines(dataset);
	}

	private static void loadExtesions() {

		if (Objects.isNull(validationService)) {
			validationService = ValidationService.get();
		}

		if (Objects.isNull(datasetService)) {
			datasetService = DatasetService.get();
		}

//		if (Objects.isNull(integrationsService)) {
//			integrationsService = ExternalService.getInstance();
//		}
	}

	private static void validate(Class<?> clazz) {
		validationService.execute(clazz);
	}

	private static void runIntegration(ExecutionPhase type) {
//		integrationsService.stream()
//			.filter(service -> service.accepted(type))
//			.forEach(ExternalService::run);
	}

}