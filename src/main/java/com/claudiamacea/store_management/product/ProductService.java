package com.claudiamacea.store_management.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductReponse findById(Integer id) {
        return productRepository.findById(id)
                .map(productMapper::toProductReponse)
                .orElseThrow(()-> new EntityNotFoundException("Product not found with id " + id));
    }
}
