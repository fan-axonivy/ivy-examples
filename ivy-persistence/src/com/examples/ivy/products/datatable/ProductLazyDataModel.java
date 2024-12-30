package com.examples.ivy.products.datatable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Tuple;

import org.apache.commons.collections4.MapUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import com.axonivy.utils.persistence.dao.QuerySettings;
import com.axonivy.utils.persistence.search.SearchFilter;
import com.examples.ivy.products.enums.InventoryStatus;
import com.examples.ivy.products.enums.ProductSearchFields;
import com.examples.ivy.products.persistence.dao.ProductDAO;
import com.examples.ivy.products.persistence.entity.Product;



public class ProductLazyDataModel extends LazyDataModel<Product> {
	private static final long serialVersionUID = 7244589026309431941L;

	@Override
	public String getRowKey(Product object) {
		return object.getId();
	}

	@Override
	public Product getRowData(String rowKey) {
		for (Product set : getWrappedData()) {
			if (set.getId().equals(rowKey)) {
				return set;
			}
		}
		return null;
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		SearchFilter searchFilter = buildSearchFilter(filterBy);
		// count row size
		int dataSize = (int) ProductDAO.getInstance().countBySearchFilter(searchFilter);
		return dataSize;
	}

	@Override
	public List<Product> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		SearchFilter searchFilter = buildSearchFilter(filterBy);

		if (MapUtils.isNotEmpty(sortBy)) {
			for (Map.Entry<String, SortMeta> entry : sortBy.entrySet()) {
				ProductSearchFields field = ProductSearchFields.valueOf(entry.getKey());
				searchFilter.addSort(field, SortOrder.ASCENDING.equals(entry.getValue().getOrder()));
			}
		}

		QuerySettings<Product> querySettings = new QuerySettings<Product>()
				.withFirstResult(first)
				.withMaxResults(pageSize);
		
		List<Tuple> found = ProductDAO.getInstance().findBySearchFilter(searchFilter, querySettings);

		return getProducts(found);
	}

	private SearchFilter buildSearchFilter(Map<String, FilterMeta> filters) {
		SearchFilter searchFilter = new SearchFilter();

		for (ProductSearchFields searchField : ProductSearchFields.values()) {
			FilterMeta value = filters.get(searchField.toString());
			if (value != null) {
				searchFilter.add(searchField, value.getFilterValue());
			} else {
				searchFilter.add(searchField);
			}
		}
		return searchFilter;
	}

	private List<Product> getProducts(List<Tuple> found) {
		List<Product> result = new ArrayList<>();
		for (Tuple tuple : found) {
			result.add(getProduct(tuple.toArray()));
		}
		return result;
	}

	private Product getProduct(Object[] row) {
		Product product = new Product();
		for (ProductSearchFields searchField : ProductSearchFields.values()) {
			Object value = row[searchField.ordinal()];
			
			switch (searchField) {
			case ID:
				product.setId(value.toString());
				break;

			case NAME:
				product.setName(Objects.toString(value, null));
				break;

			case DESCRIPTION:
				product.setDescription(Objects.toString(value, null));
				break;

			case CATEGORY:
				product.setCategory(null);
				break;

			case PRICE:
				product.setPrice(new BigDecimal(Objects.toString(value, "0")));
				break;

			case QUANTITY:
				product.setQuantity(Integer.valueOf(Objects.toString(value, "0")));
				break;

			case RATING:
				product.setRating(new BigDecimal(Objects.toString(value, "0")));
				break;
				
			case AVAILABLE_AT:
				product.setAvailableAt(Date.from(Instant.parse(Objects.toString(value, null))));
				break;
				
			case STATUS:
				product.setStatus(InventoryStatus.valueOf(Objects.toString(value, null)));
				break;
			}
		}
		return product;
	}

}
