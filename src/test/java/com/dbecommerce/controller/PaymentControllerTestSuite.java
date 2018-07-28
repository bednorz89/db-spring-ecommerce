package com.dbecommerce.controller;

import com.dbecommerce.domain.dto.*;
import com.dbecommerce.mapper.PaymentMapper;
import com.dbecommerce.service.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PaymentService paymentService;
    @MockBean
    private PaymentMapper paymentMapper;

    @Test
    public void shouldFetchPayments() throws Exception {
        //Giver
        List<PaymentDto> payments = new ArrayList<>();
        payments.add(new PaymentDto(12L, true));
        //When
        when(paymentMapper.mapToListPaymentDto(paymentService.getPayments())).thenReturn(payments);
        //Then
        mockMvc.perform(get("/v1/payments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(12)))
                .andExpect(jsonPath("$[0].paid", is(true)));
    }

    @Test
    public void shouldFetchPayment() throws Exception {
        //Giver
        PaymentDto paymentDto = new PaymentDto(13L, true);
        //When
        when(paymentMapper.mapToPaymentDto(paymentService.getPayment(13L))).thenReturn(paymentDto);
        //Then
        mockMvc.perform(get("/v1/payments/{id}", 13L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(13)))
                .andExpect(jsonPath("$.paid", is(true)));
    }

}
