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
    private Long id;

    @OneToMany(
            targetEntity = Item.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Item> items = new ArrayList<>();
}
