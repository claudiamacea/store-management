package com.claudiamacea.store_management.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT_CATEGORY")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String description;
    //TODO with @EntityListeners(AuditingEntityListener.class)
//    private LocalDateTime createdDate;
//    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
