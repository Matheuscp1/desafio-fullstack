package com.simplesdental.product.service;

import com.simplesdental.product.dto.ProductInputDTO;
import com.simplesdental.product.model.Product;
import com.simplesdental.product.repository.ProductRepository;
import com.simplesdental.product.utils.PageableQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;



    private ProductInputDTO product;

    @BeforeEach
    void setUp() {
        product = new ProductInputDTO();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("19.99"));
        product.setStatus(true);
        product.setCode("TP001");
        product.setCategoryId(1L);
    }

    @Test
    void shouldSaveProduct() {
        Product entitie = product.toEntitie();
        when(productRepository.save(any(Product.class))).thenReturn(entitie);

        Product savedProduct = productService.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isEqualTo(1L);
        assertThat(savedProduct.getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).save(any(Product.class));
    }


    @Test
    void shouldGetAllProducts() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Product> page = new PageImpl<>(Arrays.asList(product.toEntitie()), pageRequest, 1);
        when(productRepository.findAll(pageRequest)).thenReturn(page);
        PageableQuery pageQuery = new PageableQuery();
        Page<Product> products = productService.findAll(pageQuery);

        assertThat(products.getContent()).isNotNull();
        assertThat(products.getTotalElements()).isEqualTo(1);
        verify(productRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void shouldGetProductById() {
        Product entitie = product.toEntitie();
        entitie.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(entitie));

        Optional<Product> foundProduct = productService.findById(1L);

        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getId()).isEqualTo(1L);
        assertThat(foundProduct.get().getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteProductById() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteById(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}