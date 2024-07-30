package com.claudiamacea.store_management.product;

import com.claudiamacea.store_management.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("{product-id}")
    public ResponseEntity<ProductReponse> findProductById(@PathVariable("product-id") Integer id){
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<ProductReponse>> findAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "0", required = false) int size
    ){
        return ResponseEntity.ok(productService.findAllProducts(page, size));
    }

    @PostMapping("")
    public ResponseEntity<Integer> saveProduct(@Valid @RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productService.save(productRequest));
    }
}
