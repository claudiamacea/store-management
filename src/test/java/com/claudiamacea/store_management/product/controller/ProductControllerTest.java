package com.claudiamacea.store_management.product.controller;

import com.claudiamacea.store_management.common.PageResponse;
import com.claudiamacea.store_management.exception.ProductNotFoundException;
import com.claudiamacea.store_management.product.dto.PriceUpdateRequest;
import com.claudiamacea.store_management.product.dto.ProductRequest;
import com.claudiamacea.store_management.product.dto.ProductResponse;
import com.claudiamacea.store_management.product.repository.ProductCategoryRepository;
import com.claudiamacea.store_management.product.repository.ProductRepository;
import com.claudiamacea.store_management.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testFindAll() throws Exception {
        int page = 0;
        int size = 10;
        PageResponse<ProductResponse> pageResponse = new PageResponse<>(
                Arrays.asList(new ProductResponse(1,"","",0.4,2,"",true,"")),
                0, 1, 1, true, true
        );

        when(productService.findAllProducts(page, size)).thenReturn(pageResponse);

        mockMvc.perform(get("/api/v1/products")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.totalElements").value(pageResponse.getTotalElements()))
                .andExpect(jsonPath("$.content").isArray());

        verify(productService, times(1)).findAllProducts(page, size);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
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

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteProductWithAdmin() throws Exception {
        Integer productId = 1;
        mockMvc.perform(delete("/api/v1/products/{product-id}", productId)
                        .with(csrf()))
                .andExpect(status().isNoContent());
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testDeleteProductForbidden() throws Exception {
        Integer productId = 1;
        mockMvc.perform(delete("/api/v1/products/{product-id}", productId)
                        .with(csrf()))
                .andExpect(status().isForbidden());
        verify(productService, times(0)).deleteProduct(productId);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updatePriceSuccess() throws Exception {
        Integer productId = 1;
        PriceUpdateRequest request = new PriceUpdateRequest();
        request.setPrice(29.99);
        ProductResponse response = new ProductResponse();
        response.setId(productId);
        response.setPrice(29.99);
        given(productService.updatePrice(eq(productId), any(PriceUpdateRequest.class))).willReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{product-id}/price", productId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.price").value(29.99))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void updatePriceUnauthorized() throws Exception {
        Integer productId = 1;
        PriceUpdateRequest request = new PriceUpdateRequest();
        request.setPrice(19.99);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{product-id}/price", productId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updatePriceProductNotFound() throws Exception {
        Integer productId = 1;
        PriceUpdateRequest request = new PriceUpdateRequest();
        request.setPrice(19.99);
        given(productService.updatePrice(anyInt(), any(PriceUpdateRequest.class)))
                .willThrow(new ProductNotFoundException("Product with id " + productId + " not found"));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{product-id}/price", productId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
}
