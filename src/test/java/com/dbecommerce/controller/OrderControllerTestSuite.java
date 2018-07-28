package com.dbecommerce.controller;

import com.dbecommerce.domain.dto.*;
import com.dbecommerce.mapper.OrderMapper;
import com.dbecommerce.mapper.PaymentMapper;
import com.dbecommerce.service.OrderService;
import com.dbecommerce.service.PaymentService;
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
@WebMvcTest(OrderController.class)
public class OrderControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private PaymentService paymentService;
    @MockBean
    private OrderMapper orderMapper;
    @MockBean
    private PaymentMapper paymentMapper;

    @Test
    public void shouldFetchAllOrders() throws Exception {
        //Giver
        List<OrderDto> orders = new ArrayList<>();
        OrderDto order = new OrderDto();
        order.setId(23L);
        order.setUserDto(new UserDto(2L, "Dawid", "Munich"));
        order.getItemsDto().add(new ItemDto(1L, new ProductDto(19L, "prod1", new BigDecimal(9)), 3));
        order.setPaymentDto(new PaymentDto(34L, true));
        orders.add(order);
        //When
        when(orderMapper.mapToListOrderDto(orderService.getOrders())).thenReturn(orders);
        //Then
        mockMvc.perform(get("/v1/orders").contentType(MediaType.APPLICATION_JSON))
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

    @Test
    public void shouldFetchOrder() throws Exception {
        //Giver
        OrderDto order = new OrderDto();
        order.setId(23L);
        order.setUserDto(new UserDto(2L, "Dawid", "Munich"));
        order.getItemsDto().add(new ItemDto(1L, new ProductDto(19L, "prod1", new BigDecimal(9)), 3));
        order.setPaymentDto(new PaymentDto(34L, true));
        //When
        when(orderMapper.mapToOrderDto(orderService.getOrder(23L))).thenReturn(order);
        //Then
        mockMvc.perform(get("/v1/orders/{id}", 23L).contentType(MediaType.APPLICATION_JSON))
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
    public void shouldPayForOrder() throws Exception {
        //Giver
        PaymentDto paymentDto = new PaymentDto(1L, true);
        //When
        when(paymentMapper.mapToPaymentDto(paymentService.payForOrder(1L))).thenReturn(paymentDto);
        //Then
        mockMvc.perform(put("/v1/orders/{id}/payments", 23L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.paid", is(true)));
    }

}
