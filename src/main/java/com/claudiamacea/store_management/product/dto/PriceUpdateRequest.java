package com.claudiamacea.store_management.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceUpdateRequest {
    @NotNull(message = "Price should not be null")
    @Positive(message = "Price should be greater than 0")
    private Double price;
}
