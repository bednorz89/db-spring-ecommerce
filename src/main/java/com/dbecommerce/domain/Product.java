package com.dbecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "PRODUCTS")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @NotNull
    @Column(name = "PRODUCT_NAME")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCER_ID")
    private Producer producer;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "productsInCart")
    private List<ShoppingCart> inCarts = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    private List<Order> ordered = new ArrayList<>();

}
