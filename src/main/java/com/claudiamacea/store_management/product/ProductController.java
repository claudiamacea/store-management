package com.claudiamacea.store_management.product;

import com.claudiamacea.store_management.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("{product-id}")
    public ResponseEntity<ProductReponse> findProductById(@PathVariable("product-id") Integer id){
        logger.info("Fetching product with id {}", id);
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<ProductReponse>> findAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "0", required = false) int size
    ){
        logger.info("Fetching all products");
        return ResponseEntity.ok(productService.findAllProducts(page, size));
    }

    @PostMapping("")
    public ResponseEntity<Integer> saveProduct(@Valid @RequestBody ProductRequest productRequest){
        logger.info("Saving product with details: {}", productRequest.toString());
        return ResponseEntity.ok(productService.save(productRequest));
    }

    @PutMapping("{product-id}")
    public ResponseEntity<ProductReponse> updateProduct(@PathVariable("product-id") Integer id,
            @Valid @RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productService.updateProduct(id, productRequest));
    }

    @DeleteMapping("/{product-id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("product-id") Integer id){
        logger.info("Deleting product with ID: {}", id);
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
