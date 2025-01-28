package org.probato.test.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.jupiter.api.extension.ExtendWith;

@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(IgnoreIfWorkflowCondition.class)
public @interface IgnoreIfWorkflow {}