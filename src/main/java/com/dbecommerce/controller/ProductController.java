package com.dbecommerce.controller;

import com.dbecommerce.domain.Product;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createProduct(@RequestBody Product product, @RequestParam Long producerId) {
        productService.saveProduct(product, producerId);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
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
