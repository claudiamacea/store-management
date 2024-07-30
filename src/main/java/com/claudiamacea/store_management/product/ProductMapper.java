package com.claudiamacea.store_management.product;

import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public ProductReponse toProductReponse(Product product){
        return ProductReponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .active(product.isActive())
                .imageUrl(product.getImageUrl())
                .category(product.getCategory().getName())
//                .createdDate(product.getCreatedDate())
//                .updatedDate(product.getUpdatedDate())
                .build();
    }

    public Product toProduct(ProductRequest productRequest){
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .active(productRequest.isActive())
                .build();
    }
}
