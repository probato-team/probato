package org.probato.engine.procedure;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.probato.api.Postcondition;
import org.probato.api.Precondition;
import org.probato.api.Procedure;
import org.probato.loader.AnnotationLoader;
import org.probato.type.PhaseType;

public class ProcedureDiscovery {

	private ProcedureDiscovery() {}

	public static ProcedureDiscovery get() {
		return new ProcedureDiscovery();
	}

	public List<ProcedureDefinition> discover(Class<?> scriptClazz) {
		return List.of(
					discover(scriptClazz, PhaseType.PRECONDITION),
					discover(scriptClazz, PhaseType.PROCEDURE),
					discover(scriptClazz, PhaseType.POSTCONDITION))
				.stream()
				.flatMap(List::stream)
				.collect(Collectors.toList());
    }

	private List<ProcedureDefinition> discover(Class<?> scriptClazz, PhaseType phase) {

		var order = 0;
		var procedures = new ArrayList<ProcedureDefinition>();
		var members = getMembers(scriptClazz, phase);
        for (var member : members) {
        	if (member instanceof Method) {
        		procedures.add(ProcedureDefinition .forMethod(phase, (Method) member, order++));
        	} else {
        		procedures.add(ProcedureDefinition .forField(phase, (Field) member, order++));
        	}
        }

        return procedures;
	}

	private List<Object> getMembers(Class<?> scriptClazz, PhaseType phase) {

		List<Object> procedures = null;
		switch (phase) {
			case PRECONDITION:
				procedures = AnnotationLoader.getProceduresScript(scriptClazz, Precondition.class);
				break;
			case PROCEDURE:
				procedures = AnnotationLoader.getProceduresScript(scriptClazz, Procedure.class);
				break;
			case POSTCONDITION:
				procedures = AnnotationLoader.getProceduresScript(scriptClazz, Postcondition.class);
				break;
		}

		return procedures;
	}

}