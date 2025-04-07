package com.simplesdental.product.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 100)
    @Size(max = 100, message = "Campo name deve ter no maximo 100 caracteres")
    private String name;

    @Column(length = 255)
    @Size(max = 255, message = "Campo description deve ter no maximo 255 caracteres")
    private String description;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties({"category"})
    private List<Product> products;

}