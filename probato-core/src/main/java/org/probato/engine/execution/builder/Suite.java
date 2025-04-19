package org.probato.engine.execution.builder;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import org.probato.core.loader.AnnotationLoader;
import org.probato.core.loader.Configuration;

public class Suite implements Serializable {

	private static final long serialVersionUID = 3877471998959560947L;

	private String code;
	private String name;
	private String description;
	private String clazz;
	private UUID projectId;
	private String projectVersion;
	private boolean deprecated;
	private Inclusion inclusion;

	private Suite(SuiteBuilder builder) {
		this.code = builder.code;
		this.name = builder.name;
		this.clazz = builder.clazz;
		this.description = builder.description;
		this.projectId = builder.projectId;
		this.projectVersion = builder.projectVersion;
		this.deprecated = builder.deprecated;
		this.inclusion = builder.inclusion;
	}

	public static SuiteBuilder builder() {
		return new SuiteBuilder();
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getClazz() {
		return clazz;
	}

	public String getDescription() {
		return description;
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

	public Inclusion getInclusion() {
		return inclusion;
	}

	public static class SuiteBuilder {

		private String code;
		private String name;
		private String clazz;
		private String description;
		private UUID projectId;
		private String projectVersion;
		private boolean deprecated;
		private Inclusion inclusion = Inclusion.AUTOMATIC;

		private SuiteBuilder() {}

		public Suite build() {
			return new Suite(this);
		}

		public SuiteBuilder clazz(Class<?> suiteClazz) {

			var configuration = Configuration.getInstance(suiteClazz);
			var target = configuration.getExecution().getTarget();
			if (Objects.nonNull(target)) {
				this.projectId = target.getProjectId();
				this.projectVersion = target.getVersion();
			}

			AnnotationLoader.getSuite(suiteClazz)
				.ifPresent(suite -> {
					this.code = suite.code();
					this.name = suite.name();
					this.clazz = suiteClazz.getName();
					this.description = suite.description();
					this.deprecated = suite.deprecated();
				});

			return this;
		}

		public SuiteBuilder code(String code) {
			this.code = code;
			return this;
		}

		public SuiteBuilder clazz(String clazz) {
			this.clazz = clazz;
			return this;
		}

		public SuiteBuilder description(String description) {
			this.description = description;
			return this;
		}

		public SuiteBuilder projectId(UUID projectId) {
			this.projectId = projectId;
			return this;
		}

		public SuiteBuilder projectVersion(String projectVersion) {
			this.projectVersion = projectVersion;
			return this;
		}

		public SuiteBuilder deprecated(boolean deprecated) {
			this.deprecated = deprecated;
			return this;
		}

		public SuiteBuilder inclusion(Inclusion inclusion) {
			this.inclusion = inclusion;
			return this;
		}
	}
}