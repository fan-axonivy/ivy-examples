package com.examples.ivy.products.bean.component;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;

import org.apache.commons.collections4.map.HashedMap;

import com.examples.ivy.core.helper.FacesMessageHelper;
import com.examples.ivy.products.persistence.dao.CategoryDAO;
import com.examples.ivy.products.persistence.entity.Category;

public interface CategoryComponent extends ValidationComponent {

	Category getAddCategory();
	
	void setAddCategory(Category category);
	
	void setCategories(List<Category> categories);

	default void onValdationAddCategory(ComponentSystemEvent event) {
		Map<UIInput, FacesMessage> uiInputErrors = new HashedMap<>();
		
		UIInput uiInputcategoryName = (UIInput) findUIComponent(event, "name");
		String newCategoryName = Objects.toString(uiInputcategoryName.getLocalValue(), EMPTY);
		List<Category> categories = CategoryDAO.getInstance().findAllByName(newCategoryName);
		
		if(isNotEmpty(categories)) {
			var message = FacesMessageHelper.createErrorMessage("Category", "The category is existed.");
			uiInputErrors.put(uiInputcategoryName, message);
		}
		
		showErrorMessages(uiInputErrors);
	}

	default void onAddCategory() {
		CategoryDAO.getInstance().save(getAddCategory());
		setAddCategory(new Category());
		setCategories(CategoryDAO.getInstance().findAll());
	}
}
