package com.dbecommerce.repository;

import com.dbecommerce.domain.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    @Override
    Payment save(Payment payment);

    @Override
    List<Payment> findAll();

}
