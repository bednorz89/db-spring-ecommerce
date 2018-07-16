package com.dbecommerce.repository;

import com.dbecommerce.domain.Producer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends CrudRepository<Producer, Long> {
}
