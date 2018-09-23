package com.dbecommerce.controller;

import com.dbecommerce.domain.Order;
import com.dbecommerce.domain.Role;
import com.dbecommerce.domain.dto.PaymentDto;
import com.dbecommerce.exception.AccessForbiddenException;
import com.dbecommerce.mapper.PaymentMapper;
import com.dbecommerce.service.OrderService;
import com.dbecommerce.service.PaymentService;
import com.dbecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(method = RequestMethod.GET)
    public List<PaymentDto> getPayments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority())
                .collect(Collectors.toSet());
        if (roles.contains(Role.ROLE_USER.name())) {
            return paymentMapper.mapToListPaymentDto(paymentService.getUserPayments(username));
        } else {
            return paymentMapper.mapToListPaymentDto(paymentService.getPayments());
        }
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PaymentDto> getPayment(@PathVariable Long id) throws AccessForbiddenException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority())
                .collect(Collectors.toSet());
        Order order = orderService.findOrderByPayment(paymentService.getPayment(id));
        if ((roles.contains(Role.ROLE_USER.name()) && !order.getUser().equals(userService.getUserByUsername(username)))) {
            throw new AccessForbiddenException(username);
        }
        return new ResponseEntity<>(paymentMapper.mapToPaymentDto(paymentService.getPayment(id)), HttpStatus.OK);
    }

}
