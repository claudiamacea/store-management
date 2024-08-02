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

    public ProductReponse updateProduct(Integer id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product with id " + id + " not found"));
        ProductCategory category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(
                        ()-> new CategoryNotFoundException("Categoria de produs cu id " + productRequest.getCategoryId() + " nu a fost gasita "));
        logger.info("Updating product with ID {} and these details {}", id, product.toString());
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setActive(productRequest.isActive());
        product.setCategory(category);
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        productRepository.save(product);
        logger.info("Product with id {} was successfully updated with this new details: {}", id, product.toString());
        return productMapper.toProductReponse(product);
    }

    public String deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product with id " + id + " not found"));
        productRepository.deleteById(id);
        logger.info("Product with ID {} was successfully deleted - {}", id, product.toString());
        return "Product with ID " + id + " was successfully deleted";
    }
}
