package com.dbecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SHOPPING_CARTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SHOPPING_CART_ID")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "JOIN_PRODUCTS_CARTS",
            joinColumns = {@JoinColumn(name = "SHOPPING_CART_ID", referencedColumnName = "SHOPPING_CART_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")}
    )
    private List<Product> productsInCart = new ArrayList<>();
}
