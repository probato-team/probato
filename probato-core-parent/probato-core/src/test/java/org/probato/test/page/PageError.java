package org.probato.test.page;

import org.probato.api.Action;
import org.probato.model.PageObject;

public class PageError implements PageObject {

	@Action("This is a action error")
	public void actionError() {
		// Action implements
		System.out.println(1/0);
	}

	@Override
	public void setDriver(Object driver) {
		// Action implements
	}

}