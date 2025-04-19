package org.probato.engine.execution.builder;

import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.probato.core.loader.AnnotationLoader;
import org.probato.core.loader.Configuration;
import org.probato.engine.execution.ExecutionContextHolder;
import org.probato.entity.model.Browser;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.DimensionMode;
import org.probato.entity.type.Evaluation;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;
import org.probato.service.DatasetService;

public class Execution implements Serializable {

	private static final long serialVersionUID = -4780661356169226052L;

	private UUID id;
	private String scriptCode;
	private String suiteCode;
	private UUID projectId;
	private String projectVersion;
	private Long increment;
	private Evaluation evaluation;
	private Relevance relevance;
	private Complexity complexity;
	private Flow flow;
	private String inclusion = "AUTOMATIC";
	private String so;
	private String browserName;
	private String browserVersion;
	private DimensionMode browserMode;
	private Boolean browserHeadless;
	private ZonedDateTime start;
	private ZonedDateTime end;
	private Long runtime;
	private String motive;
	private UUID image;
	private UUID video;
	private List<String> datasetFilePath;
	private List<String> datasetHeaders;
	private List<String> datasetContent;
	private Map<String, List<String>> sqlFilesPath;
	private List<Step> preconditions;
	private List<Step> procedures;
	private List<Step> postconditions;
	private List<Step> stepsPreconditions;
	private List<Step> stepsProcedures;
	private List<Step> stepsPostconditions;

	private Execution(ExecutionBuilder builder) {
		this.id = builder.id;
		this.scriptCode = builder.scriptCode;
		this.suiteCode = builder.suiteCode;
		this.projectId = builder.projectId;
		this.projectVersion = builder.projectVersion;
		this.increment = builder.increment;
		this.evaluation = builder.evaluation;
		this.relevance = builder.relevance;
		this.complexity = builder.complexity;
		this.flow = builder.flow;
		this.so = builder.so;
		this.browserName = builder.browserName;
		this.browserVersion = builder.browserVersion;
		this.browserMode = builder.browserMode;
		this.browserHeadless = builder.browserHeadless;
		this.start = builder.start;
		this.end = builder.end;
		this.runtime = builder.runtime;
		this.motive = Optional.ofNullable(builder.motive).map(Throwable::getMessage).orElse(null);
		this.image = builder.image;
		this.video = builder.video;
		this.datasetFilePath = builder.datasetFilePath;
		this.datasetHeaders = builder.datasetHeaders;
		this.datasetContent = builder.datasetContent;
		this.sqlFilesPath = builder.sqlFilesPath;
		this.preconditions = builder.preconditions;
		this.procedures = builder.procedures;
		this.postconditions = builder.postconditions;
		this.stepsPreconditions = builder.stepsPreconditions;
		this.stepsProcedures = builder.stepsProcedures;
		this.stepsPostconditions = builder.stepsPostconditions;
	}

	public static ExecutionBuilder builder() {
		return new ExecutionBuilder();
	}

	public UUID getId() {
		return id;
	}

	public String getScriptCode() {
		return scriptCode;
	}

	public String getSuiteCode() {
		return suiteCode;
	}

	public UUID getProjectId() {
		return projectId;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

	public Long getIncrement() {
		return increment;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public Relevance getRelevance() {
		return relevance;
	}

	public Complexity getComplexity() {
		return complexity;
	}

	public Flow getFlow() {
		return flow;
	}

	public String getInclusion() {
		return inclusion;
	}

	public String getSo() {
		return so;
	}

	public String getBrowserName() {
		return browserName;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public DimensionMode getBrowserMode() {
		return browserMode;
	}

	public Boolean getBrowserHeadless() {
		return browserHeadless;
	}

	public ZonedDateTime getStart() {
		return start;
	}

	public ZonedDateTime getEnd() {
		return end;
	}

	public Long getRuntime() {
		return runtime;
	}

	public String getMotive() {
		return motive;
	}

	public UUID getImage() {
		return image;
	}

	public UUID getVideo() {
		return video;
	}

	public List<String> getDatasetFilePath() {
		return datasetFilePath;
	}

	public List<String> getDatasetHeaders() {
		return datasetHeaders;
	}

	public List<String> getDatasetContent() {
		return datasetContent;
	}

	public Map<String, List<String>> getSqlFilesPath() {
		return sqlFilesPath;
	}

	public List<Step> getPreconditions() {
		return preconditions;
	}

	public List<Step> getProcedures() {
		return procedures;
	}

	public List<Step> getPostconditions() {
		return postconditions;
	}

	public List<Step> getStepsPreconditions() {
		return stepsPreconditions;
	}

	public List<Step> getStepsProcedures() {
		return stepsProcedures;
	}

	public List<Step> getStepsPostconditions() {
		return stepsPostconditions;
	}

	public static class ExecutionBuilder {

		private static final String ENV_NAME = "%s (%s)";
		private static final String ENV_OS_NAME = "os.name";
		private static final String ENV_OS_ARCH = "os.arch";

		private UUID id;
		private String scriptCode;
		private String suiteCode;
		private UUID projectId;
		private String projectVersion;
		private Long increment;
		private Evaluation evaluation;
		private Relevance relevance;
		private Complexity complexity;
		private Flow flow;
		private String so;
		private String browserName;
		private String browserVersion;
		private DimensionMode browserMode;
		private Boolean browserHeadless;
		private ZonedDateTime start;
		private ZonedDateTime end;
		private Long runtime;
		private Throwable motive;
		private UUID image;
		private UUID video;
		private List<String> datasetFilePath;
		private List<String> datasetHeaders;
		private List<String> datasetContent;
		private Map<String, List<String>> sqlFilesPath = new HashMap<>();
		private List<Step> preconditions = new ArrayList<>();
		private List<Step> procedures = new ArrayList<>();
		private List<Step> postconditions = new ArrayList<>();
		private List<Step> stepsPreconditions = new ArrayList<>();
		private List<Step> stepsProcedures = new ArrayList<>();
		private List<Step> stepsPostconditions = new ArrayList<>();

		private ExecutionBuilder() {}

		public Execution build() {
			return new Execution(this);
		}

		public ExecutionBuilder id(UUID id) {
			this.id = id;
			return this;
		}

		public ExecutionBuilder project() {
			
			var configuration = Configuration.getInstance();
			this.increment = configuration.getExecution().getIncrement();
			
			var target = configuration.getExecution().getTarget();
			if (Objects.nonNull(target)) {
				this.projectId = target.getProjectId();
				this.projectVersion = target.getVersion();
			}
			
			return this;
		}

		public ExecutionBuilder env() {
			this.so = String.format(ENV_NAME, System.getProperty(ENV_OS_NAME), System.getProperty(ENV_OS_ARCH));
			return this;
		}

		public ExecutionBuilder suite(Class<?> suiteClazz) {
			AnnotationLoader.getSuite(suiteClazz).ifPresent(suite -> this.suiteCode = suite.code());
			return this;
		}

		public ExecutionBuilder script(Class<?> scriptClazz) {
			AnnotationLoader.getScript(scriptClazz).ifPresent(script -> {
				this.scriptCode = script.code();
				this.relevance = script.relevance();
				this.complexity = script.complexity();
				this.flow = script.flow();
			});
			return this;
		}

		public ExecutionBuilder evaluation(Evaluation evaluation) {
			this.evaluation = evaluation;
			return this;
		}

		public ExecutionBuilder dataset(Class<?> scriptClazz, Integer datasetLine) {
			AnnotationLoader.getDataset(scriptClazz)
				.ifPresent(dataset -> {
					var content = DatasetService.getInstance().getContent(dataset, datasetLine);
					this.datasetFilePath = Arrays.asList(dataset.value());
					this.datasetHeaders = Arrays.asList(content.getHeaders());
					this.datasetContent = Arrays.asList(content.getData());
				});
			return this;
		}

		public ExecutionBuilder browser(Browser browser, String browserName, String browserVersion) {

			this.browserMode = browser.getDimension().getMode();
			this.browserHeadless = browser.isHeadless();
			this.browserName = browserName;
			this.browserVersion = browserVersion;

			return this;
		}

		public ExecutionBuilder sql(Class<?> clazz) {
			sqlFilesPath.putAll(AnnotationLoader.getSqlPaths(clazz));
			return this;
		}

		public ExecutionBuilder start(ZonedDateTime start) {
			 this.start = start;
			return this;
		}

		public ExecutionBuilder end(ZonedDateTime end) {
			this.end = end;
			this.runtime = Duration.between(this.start, this.end).toMillis();
			return this;
		}

		public ExecutionBuilder preconditions(List<Step> actions) {
			this.preconditions.addAll(actions);
			return this;
		}

		public ExecutionBuilder procedures(List<Step> actions) {
			this.procedures.addAll(actions);
			return this;
		}

		public ExecutionBuilder postconditions(List<Step> actions) {
			this.postconditions.addAll(actions);
			return this;
		}

		public ExecutionBuilder stepsPreconditions(List<Step> actions) {
			this.stepsPreconditions.addAll(actions);
			return this;
		}

		public ExecutionBuilder stepsProcedures(List<Step> actions) {
			this.stepsProcedures.addAll(actions);
			return this;
		}

		public ExecutionBuilder stepsPostconditions(List<Step> actions) {
			this.stepsPostconditions.addAll(actions);
			return this;
		}

		public ExecutionBuilder image(UUID image) {
			this.image = image;
			return this;
		}

		public ExecutionBuilder video(UUID video) {
			this.video = video;
			return this;
		}

		public ExecutionBuilder motive(Throwable motive) {
			if (Objects.nonNull(ExecutionContextHolder.getThrowable())) {
				this.motive = ExecutionContextHolder.getThrowable();
			} else {
				this.motive = motive;
			}
			return this;
		}
	}
}