package com.claudiamacea.store_management;

import com.claudiamacea.store_management.product.entity.Product;
import com.claudiamacea.store_management.product.entity.ProductCategory;
import com.claudiamacea.store_management.product.repository.ProductCategoryRepository;
import com.claudiamacea.store_management.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StoreManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreManagementApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner startup(ProductRepository productRepository,
//									 ProductCategoryRepository categoryRepository) {
//		return args -> {
//			categoryRepository.save(
//					ProductCategory.builder()
//							.name("panificatie")
//							.description("Produse de panificatie")
//							.build()
//			);
//
//			productRepository.save(
//					Product.builder()
//							.name("paine")
//							.description("paine de casa cu maia")
//							.price(10.3)
//							.quantity(100)
//							.category(categoryRepository.findById(1)
//									.orElseThrow(()-> new EntityNotFoundException("Categorie negasita")))
//							.build()
//			);
//		};
//	}
}
