package com.claudiamacea.store_management.product.controller;

import com.claudiamacea.store_management.product.dto.ProductRequest;
import com.claudiamacea.store_management.product.dto.ProductResponse;
import com.claudiamacea.store_management.product.repository.ProductCategoryRepository;
import com.claudiamacea.store_management.product.repository.ProductRepository;
import com.claudiamacea.store_management.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductCategoryRepository productCategoryRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testFindProductById() throws Exception {
        int productId = 1;

        ProductResponse productResponse = ProductResponse.builder()
                .id(productId)
                .name("Paine")
                .description("Feliata 200 gr")
                .category("Panificatie")
                .price(7.5)
                .quantity(100)
                .active(true)
                .build();

        when(productService.findById(productId)).thenReturn(productResponse);

        mockMvc.perform(get("/api/v1/products/{product-id}", productId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productResponse.getId()))
                .andExpect(jsonPath("$.name").value(productResponse.getName()))
                .andExpect(jsonPath("$.description").value(productResponse.getDescription()))
                .andExpect(jsonPath("$.category").value(productResponse.getCategory()))
                .andExpect(jsonPath("$.price").value(productResponse.getPrice()))
                .andExpect(jsonPath("$.quantity").value(productResponse.getQuantity()))
                .andExpect(jsonPath("$.active").value(productResponse.isActive()));

        verify(productService, times(1)).findById(productId);
    }
//
//    @Test
//    void testFindAll() throws Exception {
//        int page = 0;
//        int size = 10;
//        PageResponse<ProductReponse> pageResponse = new PageResponse<>(/* initialize fields */);
//
//        when(productService.findAllProducts(page, size)).thenReturn(pageResponse);
//
//        mockMvc.perform(get("/api/v1/products")
//                        .param("page", String.valueOf(page))
//                        .param("size", String.valueOf(size)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").isArray());
//
//        verify(productService, times(1)).findAllProducts(page, size);
//    }
//
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testSaveProduct() throws Exception {
        ProductRequest productRequest = ProductRequest.builder()
                .name("Rosii")
                .description("Rosii cherry")
                .quantity(0)
                .categoryId(4)
                .price(7.9)
                .build();

        int productId = 1;

        when(productService.save(any(ProductRequest.class))).thenReturn(productId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(productId)));

        verify(productService, times(1)).save(any(ProductRequest.class));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testSaveProductInvalidField() throws Exception {
        ProductRequest productRequest = ProductRequest.builder()
                //.name("Rosii")
                .description("Rosii cherry")
                .quantity(0)
                .categoryId(4)
                .price(7.9)
                .build();

        int productId = 1;

        when(productService.save(any(ProductRequest.class))).thenReturn(productId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors").exists())
                .andExpect(jsonPath("$.validationErrors").value("Product name should not be empty"));

        verify(productService, never()).save(any(ProductRequest.class));
    }
//
//    @Test
//    void testUpdateProduct() throws Exception {
//        int productId = 1;
//        ProductRequest productRequest = new ProductRequest(/* initialize fields */);
//        ProductReponse productReponse = new ProductReponse(/* initialize fields */);
//
//        when(productService.updateProduct(eq(productId), any(ProductRequest.class))).thenReturn(productReponse);
//
//        mockMvc.perform(put("/api/v1/products/{product-id}", productId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(productRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.field").value(productReponse.getField()));
//
//        verify(productService, times(1)).updateProduct(eq(productId), any(ProductRequest.class));
//    }
//
//    @Test
//    void testDeleteProduct() throws Exception {
//        int productId = 1;
//        String responseMessage = "Product deleted successfully";
//
//        when(productService.deleteProduct(productId)).thenReturn(responseMessage);
//
//        mockMvc.perform(delete("/api/v1/products/{product-id}", productId))
//                .andExpect(status().isOk())
//                .andExpect(content().string(responseMessage));
//
//        verify(productService, times(1)).deleteProduct(productId);
//    }
}
