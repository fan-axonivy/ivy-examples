package com.examples.ivy.products.persistence.entity;

import com.examples.ivy.products.enums.InventoryStatus;
import java.math.BigDecimal;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ extends com.examples.ivy.products.persistence.entity.CustomAuditableEntity_ {

	public static volatile SingularAttribute<Product, Integer> quantity;
	public static volatile SingularAttribute<Product, BigDecimal> price;
	public static volatile SingularAttribute<Product, Instant> availableAt;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, BigDecimal> rating;
	public static volatile SingularAttribute<Product, String> description;
	public static volatile SingularAttribute<Product, Category> category;
	public static volatile SingularAttribute<Product, InventoryStatus> status;

	public static final String QUANTITY = "quantity";
	public static final String PRICE = "price";
	public static final String AVAILABLE_AT = "availableAt";
	public static final String NAME = "name";
	public static final String RATING = "rating";
	public static final String DESCRIPTION = "description";
	public static final String CATEGORY = "category";
	public static final String STATUS = "status";

}

