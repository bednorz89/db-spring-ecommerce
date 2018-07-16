package com.dbecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "JOIN_PRODUCTS_ORDERS",
            joinColumns = {@JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")}
    )
    private List<Product> products = new ArrayList<>();

    private boolean paid;

    private boolean shipped;

    private boolean canceled;

}
