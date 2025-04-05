package com.simplesdental.product.openapi;

import com.simplesdental.product.model.Product;
import com.simplesdental.product.swagger.annotations.PageableParameter;
import com.simplesdental.product.utils.PageableQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Produtos-v2")
public interface ProductControllerV2OpenApi {

	@Operation(summary = "Lista os produtos")
	@PageableParameter
	Page<Product> getAllProducts(
			@Parameter(hidden = true) PageableQuery filter);

	@Operation(summary = "Lista o produto por id")
	ResponseEntity<Product> getProductById(@Parameter(description = "Id ", example = "aId", required = true) final Long id);

	@Operation(summary = "Cria um produto")
	Product createProduct(@RequestBody Product product);

	@Operation(summary = "Atualiza um produto")
	ResponseEntity<Product> updateProduct(@Parameter(description = "Id ", example = "aId", required = true) final Long id,
						  @RequestBody Product product);


	@Operation(summary = "Deleta o produto por id")
	ResponseEntity<Void> deleteProduct(@Parameter(description = "Id ", example = "aId", required = true) final Long id);


}
