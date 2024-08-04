package com.claudiamacea.store_management.product.service;

import com.claudiamacea.store_management.common.PageResponse;
import com.claudiamacea.store_management.product.dto.PriceUpdateRequest;
import com.claudiamacea.store_management.product.dto.ProductResponse;
import com.claudiamacea.store_management.product.dto.ProductRequest;

public interface ProductService {

    ProductResponse findById(Integer id);
    PageResponse<ProductResponse> findAllProducts(int page, int size);
    Integer save(ProductRequest productRequest);
    ProductResponse updateProduct(Integer id, ProductRequest productRequest);
    void deleteProduct(Integer id);
    ProductResponse updatePrice(Integer id, PriceUpdateRequest priceUpdateRequest);
}
