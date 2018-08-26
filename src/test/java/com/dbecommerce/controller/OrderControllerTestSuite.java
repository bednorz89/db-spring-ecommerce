package com.dbecommerce.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
public class OrderControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldFetchAllOrders() throws Exception {
        //Given & //When & //Then
        mockMvc.perform(get("/v1/orders").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].userDto.id", is(2)))
                .andExpect(jsonPath("$[0].userDto.name", is("Wladimir")))
                .andExpect(jsonPath("$[0].userDto.address", is("Moscow")))
                .andExpect(jsonPath("$[0].itemsDto", hasSize(2)))
                .andExpect(jsonPath("$[0].paymentDto.paid", is(false)))
                .andExpect(jsonPath("$[0].canceled", is(false)));
    }

    @Test
    public void shouldFetchOrder() throws Exception {
        //Given & //When & //Then
        mockMvc.perform(get("/v1/orders/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.userDto.id", is(2)))
                .andExpect(jsonPath("$.userDto.name", is("Wladimir")))
                .andExpect(jsonPath("$.userDto.address", is("Moscow")))
                .andExpect(jsonPath("$.itemsDto", hasSize(2)))
                .andExpect(jsonPath("$.paymentDto.paid", is(false)))
                .andExpect(jsonPath("$.canceled", is(false)));
    }

    @Test
    public void shouldPayForOrder() throws Exception {
        //Given & //When & //Then
        mockMvc.perform(put("/v1/orders/{id}/payments", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.paid", is(true)));
    }
}
