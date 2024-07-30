package com.claudiamacea.store_management.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductRequest {
    private Integer id;
    @NotNull(message = "100")
    @NotEmpty(message = "100")
    private String name;
    @NotNull(message = "101")
    @NotEmpty(message = "101")
    private String description;
    @NotNull(message = "102")
    private Double price;
    @NotNull(message = "103")
    private Integer quantity;
    private boolean active;
    @NotNull(message = "104")
    private Integer categoryId;
}
