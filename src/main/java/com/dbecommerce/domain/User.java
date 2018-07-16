package com.dbecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "USER_NAME")
    private String name;

    @NotNull
    @Column(name = "USER_ADDRESS")
    private String address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "SHOPPING_CART_ID")
    private ShoppingCart shoppingCart;

    @OneToMany(
            targetEntity = Order.class,
            mappedBy = "user"
    )
    private List<Order> orders = new ArrayList<>();

}
