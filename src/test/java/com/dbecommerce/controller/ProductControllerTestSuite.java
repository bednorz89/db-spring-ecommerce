package com.dbecommerce.controller;

import com.dbecommerce.domain.dto.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
public class ProductControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldFetchAllProducts() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(get("/v1/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Office")))
                .andExpect(jsonPath("$[0].price", is(49.99)));
    }

    @Test
    public void shouldFetchProduct() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(get("/v1/products/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Office")))
                .andExpect(jsonPath("$.price", is(49.99)));
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        //Given
        ProductDto product = new ProductDto();
        product.setName("Pixel");
        product.setPrice(new BigDecimal(399));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(product);
        //When & //Then
        mockMvc.perform(post("/v1/products").content(json).param("producerId", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        //Giver
        ProductDto product = new ProductDto(2L, "WindowsXP", new BigDecimal(99));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(product);
        //When & //Then
        mockMvc.perform(put("/v1/products").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("WindowsXP")));
    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(delete("/v1/products/{id}", 1).contentType(MediaType.APPLICATION_JSON))
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
