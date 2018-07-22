package com.dbecommerce.repository;

import com.dbecommerce.domain.Order;
import com.dbecommerce.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Override
    Order save(Order Order);

    List<Order> findAllByUser(User user);

    @Override
    List<Order> findAll();
}
