package org.probato.model;

import java.util.UUID;

import org.probato.type.Inclusion;

/**
 * Represents a suite of scripts.
 */
public interface Suite {

	String code();

	String name();

	String description();

	String clazz();

	UUID projectId();

	String projectVersion();

	boolean deprecated();

	Inclusion inclusion();

}