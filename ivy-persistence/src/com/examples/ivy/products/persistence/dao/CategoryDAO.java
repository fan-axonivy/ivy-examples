package com.examples.ivy.products.persistence.dao;

import static java.util.Collections.emptyList;

import java.util.List;

import javax.persistence.criteria.Predicate;

import com.axonivy.utils.persistence.dao.AuditableDAO;
import com.axonivy.utils.persistence.dao.CriteriaQueryContext;
import com.examples.ivy.products.persistence.entity.Category;
import com.examples.ivy.products.persistence.entity.Category_;

public class CategoryDAO extends AuditableDAO<Category_, Category> implements IBaseDAO {
	private static final CategoryDAO instance = new CategoryDAO();

	private CategoryDAO() {
	}

	@Override
	protected Class<Category> getType() {
		return Category.class;
	}

	public static synchronized CategoryDAO getInstance() {
		return instance;
	}

	public List<Category> findAllByName(String name) {
		List<Category> categories = emptyList();
		try (CriteriaQueryContext<Category> query = initializeQuery();) {
			Predicate namePredicate = query.c.equal(query.r.get(Category_.name), name);
			
			query.where(namePredicate);
			categories = findByCriteria(query);
		} catch (Exception e) {
			LOG.error(e);
		}
		return categories;
	}
}
