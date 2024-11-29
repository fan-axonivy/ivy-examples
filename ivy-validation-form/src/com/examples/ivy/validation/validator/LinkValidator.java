package com.examples.ivy.validation.validator;

import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

@FacesValidator("com.examples.ivy.LinkValidator")
public class LinkValidator implements Validator {

	@Override
	public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {

		String urlValue = Objects.toString(value, StringUtils.EMPTY);
		UrlValidator validator = new UrlValidator();
		if (StringUtils.isNotBlank(urlValue)) {
			boolean validUrl = validator.isValid(urlValue);
			if (!validUrl) {
				FacesMessage msg = new FacesMessage("Link validation failed.", "Invalid link format.");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
		}
	}
}
