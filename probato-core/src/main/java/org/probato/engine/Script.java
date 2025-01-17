package org.probato.engine;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.probato.loader.AnnotationLoader;
import org.probato.loader.Configuration;
import org.probato.model.type.Complexity;
import org.probato.model.type.Flow;
import org.probato.model.type.Relevance;

public class Script implements Serializable {

	private static final long serialVersionUID = 7975675297366534216L;

	private String code;
	private String name;
	private String clazz;
	private String description;
	private String suiteCode;
	private UUID projectId;
	private String projectVersion;
	private boolean deprecated;
	private List<Step> preconditions;
	private List<Step> procedures;
	private List<Step> postconditions;
	private Relevance relevance;
	private Complexity complexity;
	private Flow flow;
	private Inclusion inclusion;

	private Script(ScriptBuilder builder) {
		this.code = builder.code;
		this.name = builder.name;
		this.description = builder.description;
		this.clazz = builder.clazz;
		this.suiteCode = builder.suiteCode;
		this.projectId = builder.projectId;
		this.projectVersion = builder.projectVersion;
		this.deprecated = builder.deprecated;
		this.preconditions = builder.preconditions;
		this.procedures = builder.procedures;
		this.postconditions = builder.postconditions;
		this.relevance = builder.relevance;
		this.complexity = builder.complexity;
		this.flow = builder.flow;
		this.inclusion = builder.inclusion;
	}

	public static ScriptBuilder builder() {
		return new ScriptBuilder();
	}

	public String getCode() {
		return code;
	}

	public String getClazz() {
		return clazz;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
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

	public String getSuiteCode() {
		return suiteCode;
	}

	public UUID getProjectId() {
		return projectId;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

	public boolean isDeprecated() {
		return deprecated;
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

	public Inclusion getInclusion() {
		return inclusion;
	}

	public static class ScriptBuilder {

		private String code;
		private String clazz;
		private String name;
		private String description;
		private String suiteCode;
		private UUID projectId;
		private String projectVersion;
		private boolean deprecated;
		private List<Step> preconditions = List.of();
		private List<Step> procedures = List.of();
		private List<Step> postconditions = List.of();
		private Relevance relevance;
		private Complexity complexity;
		private Flow flow;
		private Inclusion inclusion = Inclusion.AUTOMATIC;

		private ScriptBuilder() {}

		public Script build() {
			return new Script(this);
		}

		public ScriptBuilder clazz(Class<?> suiteClazz, Class<?> scriptClazz) {

			var configuration = Configuration.getInstance(scriptClazz);
			var target = configuration.getExecution().getTarget();
			if (Objects.nonNull(target)) {
				this.projectId = target.getProjectId();
				this.projectVersion = target.getVersion();
			}

			AnnotationLoader.getSuite(suiteClazz).ifPresent(suite -> this.suiteCode = suite.code());
			AnnotationLoader.getScript(scriptClazz)
				.ifPresent(script -> {
					this.code = script.code();
					this.name = script.name();
					this.description = script.description();
					this.clazz = scriptClazz.getName();
					this.deprecated = script.deprecated();
					this.relevance = script.relevance();
					this.complexity = script.complexity();
					this.flow = script.flow();
				});

			return this;
		}

		public ScriptBuilder code(String code) {
			this.code = code;
			return this;
		}

		public ScriptBuilder name(String name) {
			this.name = name;
			return this;
		}

		public ScriptBuilder description(String description) {
			this.description = description;
			return this;
		}

		public ScriptBuilder clazz(String clazz) {
			this.clazz = clazz;
			return this;
		}

		public ScriptBuilder projectId(UUID projectId) {
			this.projectId = projectId;
			return this;
		}

		public ScriptBuilder projectVersion(String projectVersion) {
			this.projectVersion = projectVersion;
			return this;
		}

		public ScriptBuilder deprecated(boolean deprecated) {
			this.deprecated = deprecated;
			return this;
		}

		public ScriptBuilder suiteCode(String suiteCode) {
			this.suiteCode = suiteCode;
			return this;
		}

		public ScriptBuilder preconditions(List<Step> preconditions) {
			this.preconditions = preconditions;
			return this;
		}

		public ScriptBuilder procedures(List<Step> procedures) {
			this.procedures = procedures;
			return this;
		}

		public ScriptBuilder postconditions(List<Step> postconditions) {
			this.postconditions = postconditions;
			return this;
		}

		public ScriptBuilder relevance(Relevance relevance) {
			this.relevance = relevance;
			return this;
		}

		public ScriptBuilder complexity(Complexity complexity) {
			this.complexity = complexity;
			return this;
		}

		public ScriptBuilder flow(Flow flow) {
			this.flow = flow;
			return this;
		}

		public ScriptBuilder inclusion(Inclusion inclusion) {
			this.inclusion = inclusion;
			return this;
		}
	}
}