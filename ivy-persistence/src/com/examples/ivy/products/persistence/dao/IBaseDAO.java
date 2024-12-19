package com.examples.ivy.products.persistence.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.axonivy.utils.persistence.dao.BaseDAO;
import ch.ivyteam.log.Logger;

public interface IBaseDAO extends BaseDAO {
	Logger LOG = Logger.getLogger(IBaseDAO.class);

	@Override
	default String getPersistenceUnitName() {
		return "my_store";
	}

	default Map<String, Object> jpaTupleToMap(javax.persistence.Tuple data) {
		final Map<String, Object> result = new LinkedHashMap<>(); // remember the ordering
		final long[] colIndex = new long[] { 0L };
		data.getElements().forEach(col -> {
			result.put(col.getAlias() != null ? col.getAlias() : String.valueOf(colIndex[0]++), data.get(col));
		});
		return result;
	}

	default List<Map<String/* UPPERCASE */, Object>> jpaTuplesToMaps(
			List<javax.persistence.Tuple> data /* CASE INSENSITIVE */) {

		return data.stream() // List<Tuple> -> Tuple1,..TupleN
				.map(tuple -> jpaTupleToMap(tuple)) // Tuple1 -> HashMap1,..TupleN -> HashMapN
				.collect(Collectors.toList()); // HashMap1,..HashMapN -> List
	}
}
