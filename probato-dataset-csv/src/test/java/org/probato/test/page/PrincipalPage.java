package org.probato.test.page;

import org.probato.api.Action;
import org.probato.api.Param;
import org.probato.entity.model.PageObject;

public class PrincipalPage implements PageObject {

	@Action("This is a action")
	public void action() {
		// Action implements
	}

	@Action("This is a action with {{VALUE}}")
	public void actionWithParam(@Param("VALUE") String value) {
		// Action implements
	}

	@Override
	public void setDriver(Object driver) {
		// Action implements
	}

}