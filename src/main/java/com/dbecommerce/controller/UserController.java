package com.dbecommerce.controller;

import com.dbecommerce.domain.Role;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<UserDto> getUsers() {
        return userMapper.mapToListUserDto(userService.getUsers());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(method = RequestMethod.GET)
    public UserDto getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userMapper.mapToUserDto(userService.getUserByUsername(username));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody UserDto userDto) {
        userService.saveUser(userMapper.mapToUser(userDto));
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority())
                .collect(Collectors.toSet());
        if (roles.contains(Role.ROLE_USER.name()) && !username.equals(userDto.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(userMapper.mapToUserDto(userService.saveUser(userMapper.mapToUser(userDto))), HttpStatus.OK);
        }
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/carts", method = RequestMethod.DELETE)
    public void deleteAllProductsFromCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteAllProductsFromCart(userService.getUserByUsername(username).getId());
    }

    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/carts", method = RequestMethod.GET)
    public List<ItemDto> showProductsFromCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return itemMapper.mapToListItemDto(userService.getProductsFromCart(userService.getUserByUsername(username).getId()));
    }

    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public OrderDto createOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return orderMapper.mapToOrderDto(orderService.createOrder(userService.getUserByUsername(username).getId()));
    }

    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public List<OrderDto> getOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return orderMapper.mapToListOrderDto(orderService.getUserAllOrders(username));
    }

}