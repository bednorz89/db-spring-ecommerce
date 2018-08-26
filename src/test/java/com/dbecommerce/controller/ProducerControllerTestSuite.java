package com.dbecommerce.controller;

import com.dbecommerce.domain.dto.ProducerDto;
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
public class ProducerControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldFetchAllProducers() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(get("/v1/producers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Google")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Microsoft")));
    }

    @Test
    public void shouldFetchProducer() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(get("/v1/producers/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Google")));
    }

    @Test
    public void shouldCreateProducer() throws Exception {
        //Giver
        ProducerDto producer = new ProducerDto();
        producer.setName("Oracle");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(producer);
        //When & //Then
        mockMvc.perform(post("/v1/producers").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateProducer() throws Exception {
        //Giver
        ProducerDto producer = new ProducerDto(2L, "MS");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(producer);
        //When & //Then
        mockMvc.perform(put("/v1/producers").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("MS")));
    }

    @Test
    public void shouldFetchProducerAllProducts() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(get("/v1/producers/{id}/products", 2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void shouldDeleteProducer() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(delete("/v1/producers/{id}", 2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}