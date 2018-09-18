package com.dbecommerce.controller;

import com.dbecommerce.domain.Role;
import com.dbecommerce.domain.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
public class UserControllerTestSuite {
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "user2")
    public void shouldFetchAllUsers() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(get("/v1/users/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("David")))
                .andExpect(jsonPath("$[0].address", is("Munich")))
                .andExpect(jsonPath("$[0].username", is("user1")))
                .andExpect(jsonPath("$[0].role[0]", is("ROLE_USER")));
    }

    @Test
    @WithUserDetails(value = "user1")
    public void shouldFetchAllUsers_accessForbiddenByRoleUser() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(get("/v1/users/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "user1")
    public void shouldFetchUser() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(get("/v1/users/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("David")))
                .andExpect(jsonPath("$.address", is("Munich")))
                .andExpect(jsonPath("$.username", is("user1")))
                .andExpect(jsonPath("$.role[0]", is("ROLE_USER")));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        //Giver
        UserDto user = new UserDto();
        user.setName("Jan");
        user.setAddress("London");
        user.setUsername("user3");
        user.getRole().add(Role.ROLE_USER);
        user.setPassword("pass3");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        //When & //Then
        mockMvc.perform(post("/v1/users").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldAdminUpdateAnyUser() throws Exception {
        //Giver
        UserDto user = new UserDto();
        user.setId(1L);
        user.setName("David");
        user.setAddress("Rome");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        //When & //Then
        mockMvc.perform(put("/v1/users").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("David")))
                .andExpect(jsonPath("$.address", is("Rome")));
    }

    @Test
    @WithUserDetails(value = "user1")
    public void shouldUserUpdateHimself() throws Exception {
        //Giver
        UserDto user = new UserDto();
        user.setId(1L);
        user.setName("David");
        user.setAddress("Rome");
        user.setUsername("user1");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        //When & //Then
        mockMvc.perform(put("/v1/users").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("David")))
                .andExpect(jsonPath("$.address", is("Rome")));
    }

    @Test
    @WithUserDetails(value = "user2")
    public void shouldNotUserUpdateAnotherUser() throws Exception {
        //Giver
        UserDto user = new UserDto();
        user.setId(1L);
        user.setName("Michal");
        user.setAddress("Rome");
        user.setUsername("user1");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        //When & //Then
        mockMvc.perform(put("/v1/users").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldDeleteUser() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(delete("/v1/users/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "user1")
    public void shouldDeleteAllProductsFromCart() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(delete("/v1/users/carts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "user1")
    public void shouldShowProductsFromCart() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(get("/v1/users/carts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].productDto.id", is(1)))
                .andExpect(jsonPath("$[0].productDto.name", is("Office")))
                .andExpect(jsonPath("$[0].productDto.price", is(49.99)))
                .andExpect(jsonPath("$[0].quantity", is(1)));
    }

    @Test
    @WithUserDetails(value = "user1")
    public void shouldCreateOrder() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(post("/v1/users/orders").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.userDto.id", is(1)))
                .andExpect(jsonPath("$.userDto.name", is("David")))
                .andExpect(jsonPath("$.userDto.address", is("Munich")))
                .andExpect(jsonPath("$.itemsDto", hasSize(2)))
                .andExpect(jsonPath("$.paymentDto.paid", is(false)))
                .andExpect(jsonPath("$.canceled", is(false)));
    }

    @Test
    @WithUserDetails(value = "user2")
    public void shouldFetchAllOrdersByUser() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(get("/v1/users/orders").contentType(MediaType.APPLICATION_JSON))
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
}
