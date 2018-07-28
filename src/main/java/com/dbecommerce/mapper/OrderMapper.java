package com.dbecommerce.mapper;

import com.dbecommerce.domain.Order;
import com.dbecommerce.domain.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private PaymentMapper paymentMapper;

    public OrderDto mapToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserDto(userMapper.mapToUserDto(order.getUser()));
        orderDto.getItemsDto().addAll(itemMapper.mapToListItemDto(order.getItems()));
        orderDto.setPaymentDto(paymentMapper.mapToPaymentDto(order.getPayment()));
        orderDto.setCanceled(order.isCanceled());
        return orderDto;
    }

    public List<OrderDto> mapToListOrderDto(List<Order> orders) {
        return orders.stream()
                .map(o -> mapToOrderDto(o))
                .collect(Collectors.toList());
    }

}
