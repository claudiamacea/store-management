package com.claudiamacea.store_management.product.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String imageUrl;
    private boolean active;
    private String category;
}
