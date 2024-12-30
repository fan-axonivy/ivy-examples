package com.examples.ivy.products.bean.component;

import static java.util.Collections.emptyList;
import static org.apache.commons.collections.MapUtils.isNotEmpty;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.commons.collections4.ListUtils;

public interface ValidationComponent {
	default void showErrorMessages(Map<UIInput, FacesMessage> uiInputErrors) {
		showErrorMessages(uiInputErrors, emptyList());
	}

	default void showErrorMessages(Map<UIInput, FacesMessage> uiInputErrors, List<FacesMessage> globalMessages) {
		FacesContext fc = FacesContext.getCurrentInstance();

		if (isNotEmpty(uiInputErrors) || isNotEmpty(globalMessages)) {
			FacesContext.getCurrentInstance().validationFailed();
		}

		for (FacesMessage msg : ListUtils.emptyIfNull(globalMessages)) {
			fc.addMessage(null, msg);
		}

		if (isNotEmpty(uiInputErrors)) {
			uiInputErrors.entrySet().forEach(it -> {
				it.getKey().setValid(false);
				if (it.getValue() != null) {
					fc.addMessage(it.getKey().getClientId(), it.getValue());
				}
			});
			fc.renderResponse();
		}
	}

	default UIComponent findUIComponent(ComponentSystemEvent event, String id) {
		UIComponent components = event.getComponent();
		UIComponent uiComponent = (UIComponent) components.findComponent(id);
		return uiComponent;
	}
	
}
