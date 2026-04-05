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
import org.probato.model.Configuration;
import org.probato.service.DatasetService;
import org.probato.service.ManagerService;
import org.probato.service.RecordService;
import org.probato.service.ValidationService;

public class Probato {

	private static ValidationService validationService;
	private static DatasetService datasetService;
	private static RecordService recordService;
	private static ManagerService managerService;
	private static Configuration configuration;

	private Probato() {}

	public static void init(Class<?> clazz) {
		loadExtesions();
		validate(clazz);
		init();
	}

	public static void init() {
		managerService.managerHealthCheck();
	}

	public static void setup() {
		managerService.loadIncrementProject();
	}

	public static void teardown() {
		managerService.sendExecutionData();
		recordService.deleteExecutionData(
				configuration.getExecution().getTarget(),
				configuration.getExecution().getDirectory());
	}

	public static void destroy() {
		// TODO execute
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
		return datasetService.countEntries(dataset);
	}

	private static void loadExtesions() {

		if (Objects.isNull(configuration)) {
			configuration = ConfigurationContext.get();
		}

		if (Objects.isNull(validationService)) {
			validationService = ValidationService.get();
		}

		if (Objects.isNull(datasetService)) {
			datasetService = DatasetService.get();
		}

		if (Objects.isNull(recordService)) {
			recordService = RecordService.get();
		}

		if (Objects.isNull(managerService)) {
			managerService = ManagerService.get();
		}
	}

	private static void validate(Class<?> clazz) {
		validationService.execute(clazz);
	}

}