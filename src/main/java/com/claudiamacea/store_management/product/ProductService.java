package com.claudiamacea.store_management.product;

import com.claudiamacea.store_management.common.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public PageResponse<ProductReponse> findAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductReponse> productReponseList =
                products.stream()
                        .map(productMapper::toProductReponse)
                        .toList();
        return new PageResponse<>(
                productReponseList,
                products.getNumber(),
                products.getNumberOfElements(),
                products.getTotalPages(),
                products.isFirst(),
                products.isLast()
        );
    }
}
