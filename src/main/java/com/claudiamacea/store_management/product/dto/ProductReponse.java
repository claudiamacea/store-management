package com.claudiamacea.store_management.product.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReponse {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String imageUrl;
    private boolean active;
//    private LocalDateTime createdDate;
//    private LocalDateTime updatedDate;
    private String category;
}
