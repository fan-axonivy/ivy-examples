package com.examples.ivy.products.persistence.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Category.class)
public abstract class Category_ extends com.examples.ivy.products.persistence.entity.CustomAuditableEntity_ {

	public static volatile SingularAttribute<Category, String> name;
	public static volatile SingularAttribute<Category, String> description;

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";

}

