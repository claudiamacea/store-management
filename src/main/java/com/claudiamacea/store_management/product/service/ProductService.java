package com.claudiamacea.store_management.product.service;

import com.claudiamacea.store_management.common.PageResponse;
import com.claudiamacea.store_management.product.dto.ProductReponse;
import com.claudiamacea.store_management.product.dto.ProductRequest;

public interface ProductService {

    ProductReponse findById(Integer id);
    PageResponse<ProductReponse> findAllProducts(int page, int size);
    Integer save(ProductRequest productRequest);
    ProductReponse updateProduct(Integer id, ProductRequest productRequest);
    String deleteProduct(Integer id);

}
