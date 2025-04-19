package org.probato.test.script;

import org.probato.api.Dataset;
import org.probato.api.Procedure;
import org.probato.api.Script;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;
import org.probato.test.datamodel.DataInvalidConstructor;

@Dataset(value = {"data/csv/file.csv"})
@Script(
	code = "UC07TC01",
	name = "Test case 07",
	description = "Test case 07",
	flow = Flow.MAIN,
	complexity = Complexity.AVERAGE,
	relevance = Relevance.AVERAGE)
public class UC07TC01_ScriptWithInvalidDatamodel {

	@Procedure
	void procedure(DataInvalidConstructor data) {
		// Procedure implements
	}

}