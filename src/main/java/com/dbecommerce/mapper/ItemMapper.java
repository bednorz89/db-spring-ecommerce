package com.dbecommerce.mapper;

import com.dbecommerce.domain.Item;
import com.dbecommerce.domain.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {

    @Autowired
    private ProductMapper productMapper;

    public ItemDto mapToItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                productMapper.mapToProductDto(item.getProduct()),
                item.getQuantity());
    }

    public List<ItemDto> mapToListItemDto(List<Item> items) {
        return items.stream()
                .map(i -> mapToItemDto(i))
                .collect(Collectors.toList());
    }

}
