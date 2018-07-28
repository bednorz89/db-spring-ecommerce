package com.dbecommerce.controller;

import com.dbecommerce.domain.dto.ProductDto;
import com.dbecommerce.mapper.ProductMapper;
import com.dbecommerce.service.ProductService;
import com.dbecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductMapper productMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<ProductDto> getProducts() {
        return productMapper.mapToListProductDto(productService.getAllProducts());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProductDto getProduct(@PathVariable Long id) {
        return productMapper.mapToProductDto(productService.getProduct(id));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createProduct(@RequestBody ProductDto productDto, @RequestParam Long producerId) {
        productService.saveProduct(productMapper.mapToProduct(productDto), producerId);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        return productMapper.mapToProductDto(productService.updateProduct(productMapper.mapToProduct(productDto)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @RequestMapping(value = "/{id}/carts", method = RequestMethod.PUT)
    public void addProductToCart(@PathVariable Long id, @RequestParam Long userId, @RequestParam Integer quantity) {
        userService.addProductToCart(id, userId, quantity);
    }

    @RequestMapping(value = "/{id}/carts", method = RequestMethod.DELETE)
    public void deleteProductFromCart(@PathVariable Long id, @RequestParam Long userId) {
        userService.deleteProductFromCart(id, userId);
    }

}
