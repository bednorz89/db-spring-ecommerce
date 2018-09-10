package com.dbecommerce.service;

import com.dbecommerce.domain.Item;
import com.dbecommerce.domain.ShoppingCart;
import com.dbecommerce.domain.User;
import com.dbecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductService productService;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findOne(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    public void addProductToCart(Long productId, Long userId, Integer quantity) {
        User user = userRepository.findOne(userId);
        if (user.getShoppingCart() == null) {
            ShoppingCart shoppingCart = new ShoppingCart();
            user.setShoppingCart(shoppingCart);
            userRepository.save(user);
        }
        Item item = new Item();
        item.setProduct(productService.getProduct(productId));
        item.setQuantity(quantity);
        user.getShoppingCart().getItems().add(item);
        userRepository.save(user);
    }

    public void deleteProductFromCart(Long productId, Long userId) {
        User user = userRepository.findOne(userId);
        List<Item> list = user.getShoppingCart().getItems().stream()
                .filter(i -> i.getProduct().equals(productService.getProduct(productId)))
                .collect(Collectors.toList());
        user.getShoppingCart().getItems().removeAll(list);
        userRepository.save(user);
    }

    public void deleteAllProductsFromCart(Long userId) {
        User user = userRepository.findOne(userId);
        user.getShoppingCart().getItems().clear();
        userRepository.save(user);
    }

    public List<Item> getProductsFromCart(Long userId) {
        return userRepository.findOne(userId).getShoppingCart().getItems();
    }

}
