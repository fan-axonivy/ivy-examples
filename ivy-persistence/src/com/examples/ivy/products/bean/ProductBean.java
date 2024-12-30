package com.examples.ivy.products.bean;

import java.util.List;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.RowEditEvent;

import com.examples.ivy.products.bean.component.CategoryComponent;
import com.examples.ivy.products.enums.InventoryStatus;
import com.examples.ivy.products.persistence.dao.CategoryDAO;
import com.examples.ivy.products.persistence.dao.ProductDAO;
import com.examples.ivy.products.persistence.entity.Category;
import com.examples.ivy.products.persistence.entity.Product;

public class ProductBean implements CategoryComponent {

	private List<Product> products;
	private List<Category> categories;
	private List<InventoryStatus> status;
	private Category addCategory;

	public List<Product> getProducts() {
		return products;
	}

	public List<Category> getCategories() {
		return categories;
	}
	
	@Override
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public List<InventoryStatus> getStatus() {
		return status;
	}

	@Override
	public Category getAddCategory() {
		return addCategory;
	}

	@Override
	public void setAddCategory(Category addCategory) {
		this.addCategory = addCategory;
	}

	public void init() {
		this.categories = CategoryDAO.getInstance().findAll();
		this.status = List.of(InventoryStatus.values());
		this.addCategory = new Category();
		this.products =  ProductDAO.getInstance().findAll();
	}

	public void onAddNewRow() {
		// Add one new product to the table:
		Product newProduct = new Product();
		products.add(newProduct);
	}
	
    public void onRowEdit(RowEditEvent<Product> event) {
    	String messages = Optional.ofNullable(event.getObject().getName()).orElse(StringUtils.EMPTY);
        FacesMessage msg = new FacesMessage("Product Edited", messages);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        ProductDAO.getInstance().save(event.getObject());
    }

    public void onRowCancel(RowEditEvent<Product> event) {
    	String messages = Optional.ofNullable(event.getObject().getName()).orElse(StringUtils.EMPTY);
        FacesMessage msg = new FacesMessage("Edit Cancelled", messages);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
