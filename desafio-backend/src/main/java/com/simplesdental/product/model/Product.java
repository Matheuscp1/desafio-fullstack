package com.simplesdental.product.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 100)
    @Size(max = 100, message = "Campo name deve ter no maximo 100 caracteres")
    private String name;

    @Size(max = 255, message = "Campo description deve ter no maximo 255 caracteres")
    private String description;

    @NotNull
    @Column(nullable = false)
    @Min(value = 1, message = "O price deve ser maior que zero.")
    private BigDecimal price;

    @NotNull
    @Column(nullable = false)
    private Boolean status;

    private String code;

    private Integer codeInt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id",nullable = false)
    @JsonIgnoreProperties({"products"})
    private Category category;

}