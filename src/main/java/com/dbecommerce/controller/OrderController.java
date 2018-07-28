package com.dbecommerce.controller;

import com.dbecommerce.domain.dto.OrderDto;
import com.dbecommerce.domain.dto.PaymentDto;
import com.dbecommerce.mapper.OrderMapper;
import com.dbecommerce.mapper.PaymentMapper;
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
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PaymentMapper paymentMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<OrderDto> getOrders() {
        return orderMapper.mapToListOrderDto(orderService.getOrders());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OrderDto getOrder(@PathVariable Long id) {
        return orderMapper.mapToOrderDto(orderService.getOrder(id));
    }

    @RequestMapping(value = "/{id}/payments", method = RequestMethod.PUT)
    public PaymentDto payForOrder(@PathVariable Long id) {
        return paymentMapper.mapToPaymentDto(paymentService.payForOrder(id));
    }

}
