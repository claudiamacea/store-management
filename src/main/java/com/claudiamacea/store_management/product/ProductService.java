package com.claudiamacea.store_management.product;

import com.claudiamacea.store_management.common.PageResponse;
import com.claudiamacea.store_management.exception.CategoryNotFoundException;
import com.claudiamacea.store_management.exception.ProductNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductReponse findById(Integer id) {
        return productRepository.findById(id)
                .map(productMapper::toProductReponse)
                .orElseThrow(()-> new ProductNotFoundException("Product with id " + id + " not found"));
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

    public Integer save(ProductRequest productRequest) {
        ProductCategory category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(
                        ()-> new CategoryNotFoundException("Categoria de produs cu id " + productRequest.getCategoryId() + " nu a fost gasita "));
        Product product = productMapper.toProduct(productRequest);
        product.setCategory(category);
        int newProductId =  productRepository.save(product).getId();
        logger.info("Product with id {} was successfully saved", newProductId);
        return newProductId;
    }
}
