package com.dbecommerce.controller;

import com.dbecommerce.domain.dto.ItemDto;
import com.dbecommerce.domain.dto.OrderDto;
import com.dbecommerce.domain.dto.UserDto;
import com.dbecommerce.mapper.ItemMapper;
import com.dbecommerce.mapper.OrderMapper;
import com.dbecommerce.mapper.UserMapper;
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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private OrderMapper orderMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> getUsers() {
        return userMapper.mapToListUserDto(userService.getUsers());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserDto getUser(@PathVariable Long id) {
        return userMapper.mapToUserDto(userService.getUser(id));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody UserDto userDto) {
        userService.saveUser(userMapper.mapToUser(userDto));
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userMapper.mapToUserDto(userService.saveUser(userMapper.mapToUser(userDto)));
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
    public List<ItemDto> showProductsFromCart(@PathVariable Long id) {
        return itemMapper.mapToListItemDto(userService.getProductsFromCart(id));
    }

    @RequestMapping(value = "/{id}/orders", method = RequestMethod.POST)
    public OrderDto createOrder(@PathVariable Long id) {
        return orderMapper.mapToOrderDto(orderService.createOrder(id));
    }

    @RequestMapping(value = "/{id}/orders", method = RequestMethod.GET)
    public List<OrderDto> getOrders(@PathVariable Long id) {
        return orderMapper.mapToListOrderDto(orderService.getUserAllOrders(id));
    }

}