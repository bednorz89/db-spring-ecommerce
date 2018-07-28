package com.dbecommerce.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private UserDto userDto;
    private List<ItemDto> itemsDto = new ArrayList<>();
    private PaymentDto paymentDto;
    private boolean canceled = false;
}
