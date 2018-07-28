package com.dbecommerce.controller;

import com.dbecommerce.domain.dto.ProducerDto;
import com.dbecommerce.domain.dto.ProductDto;
import com.dbecommerce.mapper.ProducerMapper;
import com.dbecommerce.mapper.ProductMapper;
import com.dbecommerce.service.ProducerService;
import com.dbecommerce.service.ProductService;
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
@WebMvcTest(ProducerController.class)
public class ProducerControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProducerService producerService;
    @MockBean
    private ProductService productService;
    @MockBean
    private ProducerMapper producerMapper;
    @MockBean
    private ProductMapper productMapper;

    @Test
    public void shouldFetchAllProducers() throws Exception {
        //Giver
        List<ProducerDto> producers = new ArrayList<>();
        producers.add(new ProducerDto(1L, "Dawid"));
        producers.add(new ProducerDto(2L, "Jan"));
        //When
        when(producerMapper.mapToListProducerDto(producerService.getAllProducers())).thenReturn(producers);
        //Then
        mockMvc.perform(get("/v1/producers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Dawid")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Jan")));
    }

    @Test
    public void shouldFetchProducer() throws Exception {
        //Giver
        ProducerDto producer = new ProducerDto(1L, "Dawid");
        //When
        when(producerMapper.mapToProducerDto(producerService.getProducer(1L))).thenReturn(producer);
        //Then
        mockMvc.perform(get("/v1/producers/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Dawid")));
    }

    @Test
    public void shouldCreateProducer() throws Exception {
        //Giver
        ProducerDto producer = new ProducerDto(1L, "Dawid");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(producer);
        //When & //Then
        mockMvc.perform(post("/v1/producers").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateProducer() throws Exception {
        //Giver
        ProducerDto producer = new ProducerDto(1L, "Dawid");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(producer);
        //When
        when(producerMapper.mapToProducerDto(producerService.saveProducer(producerMapper.mapToProducer(producer)))).thenReturn(producer);
        //Then
        mockMvc.perform(put("/v1/producers").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Dawid")));
    }

    @Test
    public void shouldFetchProducerAllProducts() throws Exception {
        //Giver
        List<ProductDto> products = new ArrayList<>();
        products.add(new ProductDto(1L, "prod1", new BigDecimal(9.99)));
        //When
        when(productMapper.mapToListProductDto(productService.getProducerAllProducts(1L))).thenReturn(products);
        //Then
        mockMvc.perform(get("/v1/producers/{id}/products", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void shouldDeleteProducer() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(delete("/v1/producers/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
