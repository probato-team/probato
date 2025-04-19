package org.probato.test.page;

import org.probato.api.Action;
import org.probato.entity.model.PageObject;

public class PageInvalidParam implements PageObject {

	@Action("This is a action with {{VALUE}}")
	public void actionWithParam(String value) {}

	@Override
	public void setDriver(Object driver) {}
}