package com.simplesdental.product.service;

import com.simplesdental.product.dto.ProductInputDTO;
import com.simplesdental.product.dto.ProductInputV2DTO;
import com.simplesdental.product.exception.NotFoundException;
import com.simplesdental.product.model.Category;
import com.simplesdental.product.model.Product;
import com.simplesdental.product.repository.ProductRepository;
import com.simplesdental.product.utils.PageableQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Page<Product> findAll(PageableQuery filter) {
        logger.info("findAll");
        Map<String, String> allowedSortFields = Map.ofEntries(
                Map.entry("id", "id")
        );
        return productRepository.findAll(PageableQuery.toPageable(filter, allowedSortFields));
    }

    public Optional<Product> findById(Long id) {
        logger.info("findById");
        return productRepository.findById(id);
    }

    public Product save(ProductInputDTO product) {
        logger.info("save");
        Optional<Category> category = Optional.ofNullable(categoryService.findById(product.getCategoryId()).orElseThrow(()
                -> new NotFoundException("Category not found")));
        Product entitie = product.toEntitie();
        entitie.setCategory(category.get());
        return productRepository.save(entitie);
    }

    public Product saveV2(ProductInputV2DTO product) {
        logger.info("save");
        Optional<Category> category = Optional.ofNullable(categoryService.findById(product.getCategoryId()).orElseThrow(()
                -> new NotFoundException("Category not found")));
        Product entitie = product.toEntitie();
        entitie.setCategory(category.get());
        return productRepository.save(entitie);
    }

    public void deleteById(Long id) {
        logger.warn("deleteById");
        productRepository.deleteById(id);
    }

    public Product update(Long id, ProductInputDTO product) {
       return productRepository.findById(id)
                .map(existingProduct -> {
                    Product entitie = product.toEntitie();
                    entitie.setId(id);
                    Optional<Category> category = Optional.ofNullable(categoryService.findById(product.getCategoryId()).orElseThrow(()
                            -> new NotFoundException("Category not found")));
                    entitie.setCategory(category.get());
                    return productRepository.save(entitie);
                })
                .orElseThrow(()
                        -> new NotFoundException("Product not found"));
    }

    public Product updateV2(Long id, ProductInputV2DTO product) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    Product entitie = product.toEntitie();
                    entitie.setId(id);
                    Optional<Category> category = Optional.ofNullable(categoryService.findById(product.getCategoryId()).orElseThrow(()
                            -> new NotFoundException("Category not found")));
                    entitie.setCategory(category.get());
                    return productRepository.save(entitie);
                })
                .orElseThrow(()
                        -> new NotFoundException("Product not found"));
    }
}