package org.probato.engine;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.probato.api.Postcondition;
import org.probato.api.Precondition;
import org.probato.api.Procedure;
import org.probato.type.PhaseType;

public class ScriptDiscovery {

	private ScriptDiscovery() {}

	public static List<ExecutableDefinition> discover(Class<?> scriptClass) {

        var list = new ArrayList<ExecutableDefinition>();
        int order = 0;

        for (Method method : scriptClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Precondition.class)) {
                list.add(ExecutableDefinition.forMethod(
                        PhaseType.PRECONDITION, method, order++));
            }
            if (method.isAnnotationPresent(Procedure.class)) {
                list.add(ExecutableDefinition.forMethod(
                        PhaseType.PROCEDURE, method, order++));
            }
            if (method.isAnnotationPresent(Postcondition.class)) {
                list.add(ExecutableDefinition.forMethod(
                        PhaseType.POSTCONDITION, method, order++));
            }
        }

        for (Field field : scriptClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Precondition.class)) {
                list.add(ExecutableDefinition.forField(
                        PhaseType.PRECONDITION, field, order++));
            }
            if (field.isAnnotationPresent(Procedure.class)) {
                list.add(ExecutableDefinition.forField(
                        PhaseType.PROCEDURE, field, order++));
            }
            if (field.isAnnotationPresent(Postcondition.class)) {
                list.add(ExecutableDefinition.forField(
                        PhaseType.POSTCONDITION, field, order++));
            }
        }

        return list;
    }

}