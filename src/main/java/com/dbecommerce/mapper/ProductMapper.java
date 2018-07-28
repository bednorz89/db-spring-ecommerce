package com.dbecommerce.mapper;

import com.dbecommerce.domain.Product;
import com.dbecommerce.domain.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductDto mapToProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice());
    }

    public Product mapToProduct(ProductDto productDto) {
        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getPrice(),
                null);
    }

    public List<ProductDto> mapToListProductDto(List<Product> products) {
        return products.stream()
                .map(p -> mapToProductDto(p))
                .collect(Collectors.toList());
    }


}
