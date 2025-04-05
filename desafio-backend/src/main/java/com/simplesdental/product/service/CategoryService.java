package com.simplesdental.product.service;

import com.simplesdental.product.model.Category;
import com.simplesdental.product.repository.CategoryRepository;
import com.simplesdental.product.utils.PageableQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);


    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> findAll(PageableQuery filter) {
        logger.info("findAll");
        Map<String, String> allowedSortFields = Map.ofEntries(
                Map.entry("id", "id")
        );
        return categoryRepository.findAll(PageableQuery.toPageable(filter,allowedSortFields));
    }


    public Optional<Category> findById(Long id) {
        logger.info("findById");

        return categoryRepository.findById(id);
    }

    public Category save(Category category) {
        logger.info("save");
        return categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        logger.info("deleteById");
        categoryRepository.deleteById(id);
    }
}