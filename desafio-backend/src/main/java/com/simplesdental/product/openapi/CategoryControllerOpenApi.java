package com.simplesdental.product.openapi;

import com.simplesdental.product.model.Category;
import com.simplesdental.product.swagger.annotations.PageableParameter;
import com.simplesdental.product.utils.PageableQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Categoria")
public interface CategoryControllerOpenApi {

	@Operation(summary = "Lista as categoria")
	@PageableParameter
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	Page<Category> getAllCategories(
			@Parameter(hidden = true) PageableQuery filter);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Lista a categoria por id")
	ResponseEntity<Category> getCategoryById(@Parameter(description = "Id ", example = "aId", required = true) final Long id);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Cria uma categoria")
	Category createCategory(@RequestBody Category category);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Atualiza uma categoria")
	ResponseEntity<Category> updateCategory(@Parameter(description = "Id ", example = "aId", required = true) final Long id,
											@RequestBody Category category);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "No Content"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Lista a categoria por id")
	ResponseEntity<Void> deleteCategory(@Parameter(description = "Id ", example = "aId", required = true) final Long id);


}
