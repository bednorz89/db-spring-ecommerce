package com.dbecommerce.repository;

import com.dbecommerce.domain.Producer;
import com.dbecommerce.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    List<Product> findAll();

    @Override
    Product save(Product product);

    List<Product> findAllByProducer(Producer producer);

}
