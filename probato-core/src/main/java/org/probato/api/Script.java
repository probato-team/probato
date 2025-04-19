package org.probato.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Script {

	String code();

	String name();

	String description();

	Flow flow() default Flow.MAIN;

	Relevance relevance() default Relevance.AVERAGE;

	Complexity complexity() default Complexity.AVERAGE;
	
	boolean deprecated() default false;

}