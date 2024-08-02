package com.claudiamacea.store_management.product.repository;

import com.claudiamacea.store_management.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
