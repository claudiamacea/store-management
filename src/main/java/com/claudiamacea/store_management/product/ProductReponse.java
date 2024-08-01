package com.claudiamacea.store_management.product;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

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
