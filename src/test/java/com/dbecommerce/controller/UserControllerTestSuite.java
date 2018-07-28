package com.dbecommerce.controller;

import com.dbecommerce.domain.dto.*;
import com.dbecommerce.mapper.ItemMapper;
import com.dbecommerce.mapper.OrderMapper;
import com.dbecommerce.mapper.UserMapper;
import com.dbecommerce.service.OrderService;
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
@WebMvcTest(UserController.class)
public class UserControllerTestSuite {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private OrderService orderService;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private ItemMapper itemMapper;
    @MockBean
    private OrderMapper orderMapper;

    @Test
    public void shouldFetchAllUsers() throws Exception {
        //Giver
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto(2L, "Dawid", "Munich"));
        //When
        when(userMapper.mapToListUserDto(userService.getUsers())).thenReturn(users);
        //Then
        mockMvc.perform(get("/v1/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].name", is("Dawid")))
                .andExpect(jsonPath("$[0].address", is("Munich")));
    }

    @Test
    public void shouldFetchUser() throws Exception {
        //Giver
        UserDto user = new UserDto(2L, "Dawid", "Munich");
        //When
        when(userMapper.mapToUserDto(userService.getUser(2L))).thenReturn(user);
        //Then
        mockMvc.perform(get("/v1/users/{id}", "2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Dawid")))
                .andExpect(jsonPath("$.address", is("Munich")));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        //Giver
        UserDto user = new UserDto(2L, "Dawid", "Munich");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        //When & //Then
        mockMvc.perform(post("/v1/users").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        //Giver
        UserDto user = new UserDto(2L, "Dawid", "Munich");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        //When
        when(userMapper.mapToUserDto(userService.saveUser(userMapper.mapToUser(user)))).thenReturn(user);
        //Then
        mockMvc.perform(put("/v1/users").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Dawid")))
                .andExpect(jsonPath("$.address", is("Munich")));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(delete("/v1/users/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteAllProductsFromCart() throws Exception {
        //Giver & //When & //Then
        mockMvc.perform(delete("/v1/users/{id}/carts", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldShowProductsFromCart() throws Exception {
        //Giver
        List<ItemDto> items = new ArrayList<>();
        items.add(new ItemDto(1L, new ProductDto(19L, "prod1", new BigDecimal(9)), 3));
        //When
        when(itemMapper.mapToListItemDto(userService.getProductsFromCart(19L))).thenReturn(items);
        //Then
        mockMvc.perform(get("/v1/users/{id}/carts", 19).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].productDto.id", is(19)))
                .andExpect(jsonPath("$[0].productDto.name", is("prod1")))
                .andExpect(jsonPath("$[0].productDto.price", is(9)))
                .andExpect(jsonPath("$[0].quantity", is(3)));
    }

    @Test
    public void shouldCreateOrder() throws Exception {
        //Giver
        OrderDto order = new OrderDto();
        order.setId(23L);
        order.setUserDto(new UserDto(2L, "Dawid", "Munich"));
        order.getItemsDto().add(new ItemDto(1L, new ProductDto(19L, "prod1", new BigDecimal(9)), 3));
        order.setPaymentDto(new PaymentDto(34L, true));
        //When
        when(orderMapper.mapToOrderDto(orderService.createOrder(2L))).thenReturn(order);
        //Then
        mockMvc.perform(post("/v1/users/{id}/orders", 2L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(23)))
                .andExpect(jsonPath("$.userDto.id", is(2)))
                .andExpect(jsonPath("$.userDto.name", is("Dawid")))
                .andExpect(jsonPath("$.userDto.address", is("Munich")))
                .andExpect(jsonPath("$.itemsDto", hasSize(1)))
                .andExpect(jsonPath("$.paymentDto.paid", is(true)))
                .andExpect(jsonPath("$.canceled", is(false)));
    }

    @Test
    public void shouldFetchAllOrdersByUser() throws Exception {
        //Giver
        List<OrderDto> orders = new ArrayList<>();
        OrderDto order = new OrderDto();
        order.setId(23L);
        order.setUserDto(new UserDto(2L, "Dawid", "Munich"));
        order.getItemsDto().add(new ItemDto(1L, new ProductDto(19L, "prod1", new BigDecimal(9)), 3));
        order.setPaymentDto(new PaymentDto(34L, true));
        orders.add(order);
        //When
        when(orderMapper.mapToListOrderDto(orderService.getUserAllOrders(2L))).thenReturn(orders);
        //Then
        mockMvc.perform(get("/v1/users/{id}/orders", 2L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(23)))
                .andExpect(jsonPath("$[0].userDto.id", is(2)))
                .andExpect(jsonPath("$[0].userDto.name", is("Dawid")))
                .andExpect(jsonPath("$[0].userDto.address", is("Munich")))
                .andExpect(jsonPath("$[0].itemsDto", hasSize(1)))
                .andExpect(jsonPath("$[0].paymentDto.paid", is(true)))
                .andExpect(jsonPath("$[0].canceled", is(false)));
    }


}
