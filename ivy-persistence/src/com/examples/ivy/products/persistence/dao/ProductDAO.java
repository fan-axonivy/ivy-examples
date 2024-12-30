package com.examples.ivy.products.persistence.dao;

import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.utils.persistence.dao.AuditableDAO;
import com.axonivy.utils.persistence.dao.CriteriaQueryGenericContext;
import com.axonivy.utils.persistence.dao.ExpressionMap;
import com.axonivy.utils.persistence.search.AttributePredicates;
import com.axonivy.utils.persistence.search.FilterPredicate;
import com.examples.ivy.products.enums.InventoryStatus;
import com.examples.ivy.products.enums.ProductSearchFields;
import com.examples.ivy.products.persistence.entity.Category;
import com.examples.ivy.products.persistence.entity.Product;
import com.examples.ivy.products.persistence.entity.Product_;


public class ProductDAO extends AuditableDAO<Product_, Product> implements IBaseDAO {
	private static final ProductDAO instance = new ProductDAO();

	private ProductDAO() {
	}

	@Override
	protected Class<Product> getType() {
		return Product.class;
	}

	public static synchronized ProductDAO getInstance() {
		return instance;
	}

	@Override
	protected AttributePredicates getAttributePredicate(CriteriaQueryGenericContext<Product, ?> query,
			FilterPredicate filterPredicate, ExpressionMap expressionMap) {
		
		Enum<?> searchEnum = filterPredicate.getSearchEnum();
		ProductSearchFields searchFields = ProductSearchFields.valueOf(searchEnum.name());
		AttributePredicates result = new AttributePredicates();		
		Expression<?> expression = null;
		
		switch (searchFields) {
		case ID:
			result.addSelection(getExpression(expressionMap, query.r, Product_.id));
			break;
		case NAME:
			addSelectionOrderAndLikeIgnoreCase(query, filterPredicate, result,
					getExpression(expressionMap, query.r, Product_.name));
			break;
		case DESCRIPTION:
			addSelectionOrderAndLikeIgnoreCase(query, filterPredicate, result,
					getExpression(expressionMap, query.r, Product_.description));
			break;
		case CATEGORY:
			expression = getExpression(expressionMap, query.r, Product_.category);
			addSelectionOrderAndIn(query, filterPredicate, result, expression, Category[].class);
			break;
		case PRICE:
			addSelectionOrderAndEqualNumber(query, filterPredicate, result, getExpression(expressionMap, query.r, Product_.price));			
			break;

		case QUANTITY:
			Expression<Integer> expressionInteger = getExpression(expressionMap, query.r, Product_.quantity);
			addSelectionOrderAndBetweenInteger(query, filterPredicate, result, expressionInteger);
			break;

		case AVAILABLE_AT:
			Expression<Instant> expressionInstant = getExpression(expressionMap, query.r, Product_.availableAt);
			addSelectionOrderAndBetweenInstant(query, filterPredicate, result, expressionInstant);
			break;
			
		case RATING:
			addSelectionOrderAndEqualNumber(query, filterPredicate, result, getExpression(expressionMap, query.r, Product_.rating));			
			break;

		case STATUS:
			expression = getExpression(expressionMap, query.r, Product_.status);
			addSelectionOrderAndIn(query, filterPredicate, result, expression, InventoryStatus.class);
			break;

		default:
			break;
		}
		return result;
	}

	// This function to allow search text with like and ignoreCase
	private boolean addSelectionOrderAndLikeIgnoreCase(CriteriaQueryGenericContext<Product, ?> query,
			FilterPredicate filterPredicate, AttributePredicates attrPredicates, Expression<String> expression) {
		
		var res = attrPredicates.addSelection(expression);
		attrPredicates.addOrder(query.c.asc(expression));
		var name = filterPredicate.getValue();
		if (name != null) {
			attrPredicates.addPredicate(query.c.like(query.c.lower(expression), "%" + name.toLowerCase() + "%"));
		}
		return res;
	}

	private <T> boolean addSelectionOrderAndIn(CriteriaQueryGenericContext<Product, ?> query,
			FilterPredicate filterPredicate, AttributePredicates attrPredicates, Expression<?> expression,
			Class<T> clazz) {
		
		boolean res = attrPredicates.addSelection(expression);
		attrPredicates.addOrder(query.c.asc(expression));

		var enumValues = filterPredicate.getValue(clazz);

		if (enumValues != null) {
			if (clazz.isArray()) {
				var setValue  = Arrays.stream((Object[]) enumValues).collect(Collectors.toSet());
				attrPredicates.addPredicate(query.in(expression, setValue));
			} else {
				In<Object> in = query.c.in(expression);
				in.value(enumValues);
				attrPredicates.addPredicate(in);
			}

		}
		return res;
	}
	
	private boolean addSelectionOrderAndBetweenInteger(CriteriaQueryGenericContext<Product, ?> query,
			FilterPredicate filterPredicate, AttributePredicates attrPredicates, Expression<Integer> expression) {
		
		var res = attrPredicates.addSelection(expression);
		attrPredicates.addOrder(query.c.asc(expression));
		
		String range = filterPredicate.getValue(String.class);
		if(range == null || range.length() == 0) {
			return res;
		}
		
		// Separate should be match with UI
		String[] inputs = range.split("-");		
		if (inputs.length == 3) {			
			Predicate predicate = query.c.between(expression, Integer.valueOf(inputs[0]), Integer.valueOf(inputs[2]));
			attrPredicates.addPredicate(predicate);
		}
		
		if (inputs.length == 2) {
			Predicate predicate = null;
			if(StringUtils.isNumeric(inputs[0])) {
				predicate = query.c.greaterThanOrEqualTo(expression, Integer.valueOf(inputs[0]));	
			} else {
				predicate = query.c.lessThanOrEqualTo(expression, Integer.valueOf(inputs[1]));	
			}			
			attrPredicates.addPredicate(predicate);
		}
		return res;
	}
	
	private boolean addSelectionOrderAndBetweenInstant(CriteriaQueryGenericContext<Product, ?> query,
			FilterPredicate filterPredicate, AttributePredicates attrPredicates, Expression<Instant> expression) {
		
		var res = attrPredicates.addSelection(expression);
		attrPredicates.addOrder(query.c.asc(expression));
		
		Long[] range = filterPredicate.getValue(Long[].class);
		
		if (range != null && range.length > 1) {
			Instant start = Instant.ofEpochMilli(range[0]);
			Instant end = Instant.ofEpochMilli(range[1]);
			Predicate predicate = query.c.between(expression, start, end);
			attrPredicates.addPredicate(predicate);
		}
		return res;
	}
	
	private boolean addSelectionOrderAndEqualNumber(CriteriaQueryGenericContext<Product, ?> query,
			FilterPredicate filterPredicate, AttributePredicates attrPredicates,
			Expression<? extends Number> expression) {
		var res = attrPredicates.addSelection(expression);
		attrPredicates.addOrder(query.c.asc(expression));
		var name = filterPredicate.getValue();
		if (name != null) {
			attrPredicates.addPredicate(query.c.equal(expression, name));
		}
		return res;
	}

}
