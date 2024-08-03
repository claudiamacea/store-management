package com.claudiamacea.store_management.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ProductRequest {
    private Integer id;
    @NotNull(message = "Product name should not be empty")
    @NotEmpty(message = "Product name should not be empty")
    private String name;
    @NotNull(message = "Product description should not be empty")
    @NotEmpty(message = "Product description should not be empty")
    private String description;
    @Min(value = 0, message = "Price shuld be greater than 0")
    private Double price;
    @NotNull(message = "Product quantity should not be empty")
    @Min(value = 0, message = "Quantity should not be less than 0")
    private Integer quantity;
    private boolean active;
    @NotNull(message = "Product category should not be empty")
    private Integer categoryId;
}
