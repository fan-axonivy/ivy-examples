package com.examples.ivy.validation.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.examples.ivy.core.helper.FacesMessageHelper;
import com.examples.ivy.core.helper.PhoneNumberHelper;
import com.examples.ivy.validation.model.User;


public class UserRegistationBean {

	private List<User> users;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void init() {
		this.user = new User();

		User newUser = new User();
		newUser.setUsername("usera");
		newUser.setEmail("usera@test.com");
		newUser.setFirstName("User");
		newUser.setLastName("A");
		newUser.setPhoneNumber("+1 202 861 0737");
		newUser.setSocialUrl("https://facebook.com/usea");

		this.users = new ArrayList<>();
		this.users.add(newUser);
	}

	public void onValidationUserInformation(ComponentSystemEvent event) {
		Map<UIInput, FacesMessage> uiInputErrors = new HashMap<>();

		UIInput uiUsername = (UIInput) findUIComponent(event, "username");
		String username = (String) uiUsername.getLocalValue();

		UIInput uiEmail = (UIInput) findUIComponent(event, "email");
		String email = (String) uiEmail.getLocalValue();

		boolean isUsernameIsExists = this.users.stream().anyMatch(it -> it.getUsername().equals(username));
		boolean isEmailIsExists = this.users.stream().anyMatch(it -> it.getEmail().equals(email));

		if (isUsernameIsExists) {
			FacesMessage existUsernameMgs = FacesMessageHelper.createErrorMessage("Username validation failed.",
					"The username is already used.");
			uiInputErrors.put(uiUsername, existUsernameMgs);
		}

		if (isEmailIsExists) {
			FacesMessage existEmailMgs = FacesMessageHelper.createErrorMessage("Email validation failed.",
					"The email is already used.");
			uiInputErrors.put(uiEmail, existEmailMgs);
		}

		showErrorMessages(uiInputErrors);

	}

	public void onValidationPhoneNumber(FacesContext context, UIComponent comp, Object value) {
		String phoneNumber = (String) value;
		if (StringUtils.isNotBlank(phoneNumber)) {
			boolean isPhoneNumber = PhoneNumberHelper.isPhoneNumber((String) value);
			if (!isPhoneNumber) {
				FacesMessage msg = FacesMessageHelper.createErrorMessage("Phone number validation failed.",
						"Invalid phone number format.");
				throw new ValidatorException(msg);
			}
		}
	}

	private UIComponent findUIComponent(ComponentSystemEvent event, String id) {
		UIComponent components = event.getComponent();
		UIComponent uiComponent = (UIComponent) components.findComponent(id);
		return uiComponent;
	}

	private void showErrorMessages(Map<UIInput, FacesMessage> uiInputErrors) {
		FacesContext fc = FacesContext.getCurrentInstance();

		if (MapUtils.isNotEmpty(uiInputErrors)) {
			FacesContext.getCurrentInstance().validationFailed();

			uiInputErrors.entrySet().forEach(it -> {
				it.getKey().setValid(false);
				if (it.getValue() != null) {
					fc.addMessage(it.getKey().getClientId(), it.getValue());
				}
			});
			fc.renderResponse();
		}
	}

}
