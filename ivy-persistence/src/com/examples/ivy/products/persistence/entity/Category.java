package com.examples.ivy.products.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category extends CustomAuditableEntity {

	private static final long serialVersionUID = 7026459413796462444L;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}