package com.dbecommerce.service;

import com.dbecommerce.domain.Order;
import com.dbecommerce.domain.Payment;
import com.dbecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;

    public Order createOrder(Long userId) {
        Order order = new Order();
        order.setUser(userService.getUser(userId));
        order.getItems().addAll(userService.getProductsFromCart(userId));
        userService.deleteAllProductsFromCart(userId);
        order.setPayment(new Payment());
        return orderRepository.save(order);
    }

    public List<Order> getUserAllOrders(String username) {
        return orderRepository.findAllByUser(userService.getUserByUsername(username));
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {
        return orderRepository.findOne(id);
    }

    public Order findOrderByPayment(Payment payment) {
        return orderRepository.findOrderByPayment(payment);
    }

}
