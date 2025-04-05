package com.simplesdental.product.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class PageableQuery {
	
	private Integer page = 0;
	private Integer size = 10;
	private List<String> sort = new ArrayList<>();
	private Boolean isPageable;

	public List<String> adjustSort(){
		if(getEmptyIfEmpty(getSort()).size() == 2) {
			if(getSort().get(1).equalsIgnoreCase("asc") || getSort().get(1).equalsIgnoreCase("desc")) {
				var newSort = new ArrayList<String>();
				
				newSort.add(getSort().get(0) + "," + getSort().get(1));
				
				return newSort;
			}
		}

		return getSort(); 
	}

	public static <T> List<T> getEmptyIfEmpty(final List<T> values) {
		if (values == null || values.isEmpty()) {
			return new ArrayList<>();
		}
		return values;
	}

	// Converte o PageableQuery para Pageable
	public static Pageable toPageable(PageableQuery filtro, Map<String, String> fieldsMapping) {
		List<Sort.Order> orders = filtro.adjustSort().stream()
				.filter(StringUtils::hasText)
				.map(sort -> {
					String[] sortField = sort.split(",");
					Sort.Direction direction = sortField.length > 1 ?
							Sort.Direction.fromOptionalString(sortField[1]).orElse(Sort.Direction.ASC) : Sort.Direction.ASC;

					return new Sort.Order(direction, sortField[0]);
				})
				.filter(order -> fieldsMapping.containsKey(order.getProperty()))
				.map(order -> new Sort.Order(order.getDirection(), fieldsMapping.get(order.getProperty())))
				.collect(Collectors.toList());

		Pageable newPageable = PageRequest.of(filtro.getPage(), filtro.getSize(), Sort.by(orders));

		//se não for paginado então edita o pageable para não retornar valores inconsistentes
		if (Boolean.FALSE.equals(filtro.getIsPageable())) {
			newPageable = PageRequest.of(0, newPageable.getPageSize(), newPageable.getSort());
		}

		return newPageable;
	}

}
