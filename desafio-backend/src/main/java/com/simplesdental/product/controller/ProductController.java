package com.simplesdental.product.controller;

import com.simplesdental.product.dto.ProductInputDTO;
import com.simplesdental.product.model.Product;
import com.simplesdental.product.openapi.ProductControllerOpenApi;
import com.simplesdental.product.service.ProductService;
import com.simplesdental.product.utils.PageableQuery;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController implements ProductControllerOpenApi {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @GetMapping
    public Page<Product> getAllProducts(PageableQuery filter) {
        Page<Product> products = productService.findAll(filter);
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
    public Product createProduct(@Valid @RequestBody ProductInputDTO product) {
        return productService.save(product);
    }

    @Override
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody ProductInputDTO product) {
        return productService.update(id,product);
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