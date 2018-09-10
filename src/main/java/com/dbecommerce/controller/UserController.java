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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public ResponseEntity<UserDto> getUser(@PathVariable Long id, Principal principal) {
        Long userId = userService.getUserByUsername(principal.getName()).getId();
        String role = userService.getUserByUsername(principal.getName()).getRole();
        if (role.equals("ROLE_USER") && userId != id) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(userMapper.mapToUserDto(userService.getUser(id)), HttpStatus.FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody UserDto userDto) {
        userService.saveUser(userMapper.mapToUser(userDto));
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, Principal principal) {
        Long userId = userService.getUserByUsername(principal.getName()).getId();
        String role = userService.getUserByUsername(principal.getName()).getRole();
        if (role.equals("ROLE_USER") && userId != userDto.getId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(userMapper.mapToUserDto(userService.saveUser(userMapper.mapToUser(userDto))), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @RequestMapping(value = "/carts", method = RequestMethod.DELETE)
    public void deleteAllProductsFromCart(Principal principal) {
        userService.deleteAllProductsFromCart(userService.getUserByUsername(principal.getName()).getId());
    }

    @RequestMapping(value = "/carts", method = RequestMethod.GET)
    public List<ItemDto> showProductsFromCart(Principal principal) {
        return itemMapper.mapToListItemDto(userService.getProductsFromCart(userService.getUserByUsername(principal.getName()).getId()));
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public OrderDto createOrder(Principal principal) {
        return orderMapper.mapToOrderDto(orderService.createOrder(userService.getUserByUsername(principal.getName()).getId()));
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public List<OrderDto> getOrders(Principal principal) {
        return orderMapper.mapToListOrderDto(orderService.getUserAllOrders(userService.getUserByUsername(principal.getName()).getId()));
    }

}