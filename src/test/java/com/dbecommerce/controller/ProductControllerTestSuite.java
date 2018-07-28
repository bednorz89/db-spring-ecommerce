package com.dbecommerce.controller;

import com.dbecommerce.domain.dto.ProductDto;
import com.dbecommerce.mapper.ProductMapper;
import com.dbecommerce.service.ProductService;
import com.dbecommerce.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private UserService userService;
    @MockBean
    private ProductMapper productMapper;

    @Test
    public void shouldFetchAllProducts() throws Exception {
        //Giver
        List<ProductDto> products = new ArrayList<>();
        products.add(new ProductDto(1L, "prod1", new BigDecimal(9)));
        products.add(new ProductDto(2L, "prod2", new BigDecimal(2)));
        //When
        when(productMapper.mapToListProductDto(productService.getAllProducts())).thenReturn(products);
        //Then
        mockMvc.perform(get("/v1/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("prod1")))
                .andExpect(jsonPath("$[0].price", is(9)));
    }

    @Test
    public void shouldFetchProduct() throws Exception {
        //Giver
        ProductDto product = new ProductDto(19L, "prod1", new BigDecimal(9));
        //When
        when(productMapper.mapToProductDto(productService.getProduct(19L))).thenReturn(product);
        //Then
        mockMvc.perform(get("/v1/products/{id}", 19).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(19)))
                .andExpect(jsonPath("$.name", is("prod1")))
                .andExpect(jsonPath("$.price", is(9)));
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        //Giver
        ProductDto product = new ProductDto(19L, "prod1", new BigDecimal(9));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(product);
        //When & //Then
        mockMvc.perform(post("/v1/products").content(json).param("producerId", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        //Giver
        ProductDto product = new ProductDto(19L, "prod1", new BigDecimal(9));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(product);
        //When
        when(productMapper.mapToProductDto(productService.updateProduct(productMapper.mapToProduct(product)))).thenReturn(product);
        //Then
        mockMvc.perform(put("/v1/products").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(19)))
                .andExpect(jsonPath("$.name", is("prod1")));
    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(delete("/v1/products/{id}", 19).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAddProductToCart() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(put("/v1/products/{id}/carts", 19)
                .param("userId", "1")
                .param("quantity", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteProductFromCart() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(delete("/v1/products/{id}/carts", 19)
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
