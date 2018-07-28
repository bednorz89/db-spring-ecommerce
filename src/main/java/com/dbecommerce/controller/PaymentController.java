package com.dbecommerce.controller;

import com.dbecommerce.domain.dto.PaymentDto;
import com.dbecommerce.mapper.PaymentMapper;
import com.dbecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentMapper paymentMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<PaymentDto> getPayments() {
        return paymentMapper.mapToListPaymentDto(paymentService.getPayments());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PaymentDto getPayment(@PathVariable Long id) {
        return paymentMapper.mapToPaymentDto(paymentService.getPayment(id));
    }

}
