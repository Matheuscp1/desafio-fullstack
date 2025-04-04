package com.simplesdental.product.service;

import com.simplesdental.product.model.Product;
import com.simplesdental.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findAll(Pageable pageable) {
        logger.info("findAll");
        return productRepository.findAll(pageable);
    }

    public Optional<Product> findById(Long id) {
        logger.info("findById");
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        logger.info("save");
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        logger.warn("deleteById");
        productRepository.deleteById(id);
    }
}