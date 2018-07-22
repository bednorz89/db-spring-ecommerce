package com.dbecommerce.controller;

import com.dbecommerce.domain.Payment;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<Payment> getPayments() {
        return paymentService.getPayments();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Payment getPayment(@PathVariable Long id) {
        return paymentService.getPayment(id);
    }

}
