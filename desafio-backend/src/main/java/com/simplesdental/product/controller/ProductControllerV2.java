package com.simplesdental.product.controller;

import com.simplesdental.product.dto.ProductInputDTO;
import com.simplesdental.product.dto.ProductInputV2DTO;
import com.simplesdental.product.model.Product;
import com.simplesdental.product.openapi.ProductControllerV2OpenApi;
import com.simplesdental.product.service.ProductService;
import com.simplesdental.product.utils.PageableQuery;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/products")
public class ProductControllerV2  implements ProductControllerV2OpenApi {

    private final ProductService productService;

    @Autowired
    public ProductControllerV2(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @GetMapping
    public Page<Product> getAllProducts(PageableQuery pageable) {
        Page<Product> products = productService.findAll(pageable);
        products.forEach(product -> {
            if (product.getCategory() != null) {
                Hibernate.initialize(product.getCategory());
            }
        });
        return products;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(product -> {
                    if (product.getCategory() != null) {
                        Hibernate.initialize(product.getCategory());
                    }
                    return ResponseEntity.ok(product);
                })
                .orElse(ResponseEntity.notFound().build());

    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody ProductInputV2DTO product) {
        return productService.saveV2(product);
    }

    @Override
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody ProductInputV2DTO product) {
        return productService.updateV2(id,product);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productService.findById(id)
                .map(product -> {
                    productService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}