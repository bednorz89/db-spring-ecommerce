package com.dbecommerce.service;

import com.dbecommerce.domain.Payment;
import com.dbecommerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderService orderService;

    public Payment payForOrder(Long id) {
        Payment payment = orderService.getOrder(id).getPayment();
        payment.setPaid(true);
        return paymentRepository.save(payment);
    }

    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPayment(Long id) {
        return paymentRepository.findOne(id);
    }

    public List<Payment> getUserPayments(String username) {
        return orderService.getOrders().stream()
                .filter(o -> o.getUser().getUsername().equals(username))
                .map(o -> o.getPayment())
                .collect(Collectors.toList());
    }

}
