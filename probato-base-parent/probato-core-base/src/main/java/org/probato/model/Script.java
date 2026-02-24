package org.probato.model;

import java.util.List;

import org.probato.type.Complexity;
import org.probato.type.Flow;
import org.probato.type.Inclusion;
import org.probato.type.Relevance;

/**
 * Represents a test script definition.
 *
 * <p>
 * A Script is the atomic executable unit from a business perspective. It
 * contains metadata for governance and classification, as well as the steps
 * organized by execution phase.
 * </p>
 *
 * <p>
 * Scripts are immutable definitions and do not contain execution state.
 * </p>
 */
public interface Script {

	/** @return unique script code */
	String code();

	/** @return human-readable script name */
	String name();

	/** @return fully qualified class name that defines the script */
	String clazz();

	/** @return business description of the script */
	String description();

	/** @return code of the suite this script belongs to */
	String suiteCode();

	/** @return project identifier */
	String projectId();

	/** @return project version */
	String projectVersion();

	/** @return whether this script is deprecated */
	boolean deprecated();

	/** @return execution inclusion rule */
	Inclusion inclusion();

	/** @return business relevance */
	Relevance relevance();

	/** @return execution complexity */
	Complexity complexity();

	/** @return execution flow classification */
	Flow flow();

	/** @return steps required before execution */
	List<Step> preconditions();

	/** @return main execution steps */
	List<Step> procedures();

	/** @return steps executed after main flow */
	List<Step> postconditions();

}