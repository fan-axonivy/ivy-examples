package com.examples.ivy.products.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.examples.ivy.products.enums.InventoryStatus;

@Entity
@Table(name = "product")
public class Product extends CustomAuditableEntity {

	private static final long serialVersionUID = 7026459413796462444L;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private BigDecimal price;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "rating")
	private BigDecimal rating;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private InventoryStatus status;

	@Column(name = "available_at")
	private Date availableAt;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public InventoryStatus getStatus() {
		return status;
	}

	public void setStatus(InventoryStatus status) {
		this.status = status;
	}

	public Date getAvailableAt() {
		return availableAt;
	}

	public void setAvailableAt(Date availableAt) {
		this.availableAt = availableAt;
	}
}
