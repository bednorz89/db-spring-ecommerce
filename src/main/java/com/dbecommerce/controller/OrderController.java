package com.dbecommerce.controller;

import com.dbecommerce.domain.Order;
import com.dbecommerce.domain.Payment;
import com.dbecommerce.service.OrderService;
import com.dbecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @RequestMapping(value = "/{id}/payments", method = RequestMethod.PUT)
    public Payment payForOrder(@PathVariable Long id) {
        return paymentService.payForOrder(id);
    }

}
