package com.dbecommerce.controller;

import com.dbecommerce.domain.Item;
import com.dbecommerce.domain.Order;
import com.dbecommerce.domain.User;
import com.dbecommerce.service.OrderService;
import com.dbecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public User updateUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @RequestMapping(value = "/{id}/carts", method = RequestMethod.DELETE)
    public void deleteAllProductsFromCart(@PathVariable Long id) {
        userService.deleteAllProductsFromCart(id);
    }

    @RequestMapping(value = "/{id}/carts", method = RequestMethod.GET)
    public List<Item> showProductsFromCart(@PathVariable Long id) {
        return userService.getProductsFromCart(id);
    }

    @RequestMapping(value = "/{id}/orders", method = RequestMethod.POST)
    public Order createOrder(@PathVariable Long id) {
        return orderService.createOrder(id);
    }

    @RequestMapping(value = "/{id}/orders", method = RequestMethod.GET)
    public List<Order> getOrders(@PathVariable Long id) {
        return orderService.getUserAllOrders(id);
    }

}