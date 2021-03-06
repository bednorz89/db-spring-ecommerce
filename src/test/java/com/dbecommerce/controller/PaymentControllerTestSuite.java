package com.dbecommerce.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
public class PaymentControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldAdminFetchAllPayments() throws Exception {
        //Given & //When & //Then
        mockMvc.perform(get("/v1/payments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].paid", is(false)));
    }

    @Test
    @WithUserDetails("user1")
    public void shouldUserFetchOwnPayments() throws Exception {
        //Given & //When & //Then
        mockMvc.perform(get("/v1/payments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithUserDetails("user2")
    public void shouldUserFetchOwnPayment() throws Exception {
        //Given & //When & //Then
        mockMvc.perform(get("/v1/payments/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.paid", is(false)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldAdminFetchPayment() throws Exception {
        //Given & //When & //Then
        mockMvc.perform(get("/v1/payments/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.paid", is(false)));
    }

    @Test
    @WithUserDetails("user1")
    public void shouldNotUserFetchNotOwnPayment() throws Exception {
        //Given & //When & //Then
        mockMvc.perform(get("/v1/payments/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

}
