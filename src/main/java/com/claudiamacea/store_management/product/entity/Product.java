package com.claudiamacea.store_management.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String imageUrl;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;
}
