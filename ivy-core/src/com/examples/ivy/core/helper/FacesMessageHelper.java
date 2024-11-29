package com.examples.ivy.core.helper;

import javax.faces.application.FacesMessage;

public class FacesMessageHelper {

	public static FacesMessage createErrorMessage(String title, String detail) {
		return new FacesMessage(FacesMessage.SEVERITY_ERROR, title, detail);
	}
}
