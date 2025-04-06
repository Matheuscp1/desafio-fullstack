package com.simplesdental.product.openapi;

import com.simplesdental.product.model.Product;
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
@Tag(name = "Produtos")
public interface ProductControllerOpenApi {

	@Operation(summary = "Lista os produtos")
	@PageableParameter
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	Page<Product> getAllProducts(
			@Parameter(hidden = true) PageableQuery filter);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Lista o produto por id")
	ResponseEntity<Product> getProductById(@Parameter(description = "Id ", example = "aId", required = true) final Long id);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Cria um produto")
	Product createProduct(@RequestBody Product product);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Atualiza um produto")
	ResponseEntity<Product> updateProduct(@Parameter(description = "Id ", example = "aId", required = true) final Long id,
						  @RequestBody Product product);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "No Content"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Deleta o produto por id")
	ResponseEntity<Void> deleteProduct(@Parameter(description = "Id ", example = "aId", required = true) final Long id);


}
