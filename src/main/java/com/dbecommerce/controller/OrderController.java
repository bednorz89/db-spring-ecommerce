package com.dbecommerce.controller;

import com.dbecommerce.domain.Order;
import com.dbecommerce.domain.dto.OrderDto;
import com.dbecommerce.domain.dto.PaymentDto;
import com.dbecommerce.mapper.OrderMapper;
import com.dbecommerce.mapper.PaymentMapper;
import com.dbecommerce.service.OrderService;
import com.dbecommerce.service.PaymentService;
import com.dbecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
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
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<OrderDto> getOrders(Principal principal) {
        Long userId = userService.getUserByUsername(principal.getName()).getId();
        String role = userService.getUserByUsername(principal.getName()).getRole();
        if (role.equals("ROLE_USER")) {
            return orderMapper.mapToListOrderDto(orderService.getUserAllOrders(userId));
        } else {
            return orderMapper.mapToListOrderDto(orderService.getOrders());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id, Principal principal) {
        String role = userService.getUserByUsername(principal.getName()).getRole();
        Order order = orderService.getOrder(id);
        if ((role.equals("ROLE_USER") && order.getUser().equals(userService.getUserByUsername(principal.getName()))) || role.equals("ROLE_ADMIN")) {
            return new ResponseEntity<>(orderMapper.mapToOrderDto(order), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/{id}/payments", method = RequestMethod.PUT)
    public ResponseEntity<PaymentDto> payForOrder(@PathVariable Long id, Principal principal) {
        Long userId = userService.getUserByUsername(principal.getName()).getId();
        if (userId == orderService.getOrder(id).getUser().getId()) {
            return new ResponseEntity<>(paymentMapper.mapToPaymentDto(paymentService.payForOrder(id)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
