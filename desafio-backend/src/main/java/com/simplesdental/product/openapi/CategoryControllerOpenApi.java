package com.simplesdental.product.openapi;

import com.simplesdental.product.model.Category;
import com.simplesdental.product.swagger.annotations.PageableParameter;
import com.simplesdental.product.utils.PageableQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Categoria")
public interface CategoryControllerOpenApi {

	@Operation(summary = "Lista as categoria")
	@PageableParameter
	Page<Category> getAllCategories(
			@Parameter(hidden = true) PageableQuery filter);

	@Operation(summary = "Lista a categoria por id")
	ResponseEntity<Category> getCategoryById(@Parameter(description = "Id ", example = "aId", required = true) final Long id);

	@Operation(summary = "Cria uma categoria")
	Category createCategory(@RequestBody Category category);

	@Operation(summary = "Atualiza uma categoria")
	ResponseEntity<Category> updateCategory(@Parameter(description = "Id ", example = "aId", required = true) final Long id,
											@RequestBody Category category);


	@Operation(summary = "Lista a categoria por id")
	ResponseEntity<Void> deleteCategory(@Parameter(description = "Id ", example = "aId", required = true) final Long id);


}
