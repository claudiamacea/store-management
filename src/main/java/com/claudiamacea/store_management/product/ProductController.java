package com.claudiamacea.store_management.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("{product-id}")
    public ResponseEntity<ProductReponse> findProductById(@PathVariable("product-id") Integer id){
        return ResponseEntity.ok(productService.findById(id));
    }
}
