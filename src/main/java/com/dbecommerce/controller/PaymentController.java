package com.dbecommerce.controller;

import com.dbecommerce.domain.Order;
import com.dbecommerce.domain.Role;
import com.dbecommerce.domain.dto.PaymentDto;
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
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public List<PaymentDto> getPayments(Principal principal) {
        Long userId = userService.getUserByUsername(principal.getName()).getId();
        Collection<Role> roles = userService.getUserByUsername(principal.getName()).getRole();
        if (roles.contains(Role.ROLE_USER)) {
            return paymentMapper.mapToListPaymentDto(paymentService.getUserPayments(userId));
        } else {
            return paymentMapper.mapToListPaymentDto(paymentService.getPayments());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PaymentDto> getPayment(@PathVariable Long id, Principal principal) {
        Collection<Role> roles = userService.getUserByUsername(principal.getName()).getRole();
        Order order = orderService.findOrderByPayment(paymentService.getPayment(id));
        if ((roles.contains(Role.ROLE_USER) && order.getUser().equals(userService.getUserByUsername(principal.getName()))) || roles.contains(Role.ROLE_ADMIN)) {
            return new ResponseEntity<>(paymentMapper.mapToPaymentDto(paymentService.getPayment(id)), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
