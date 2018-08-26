package com.dbecommerce.repository;

import com.dbecommerce.domain.Producer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProducerRepository extends CrudRepository<Producer, Long> {

    @Override
    List<Producer> findAll();

    @Override
    Producer save(Producer producer);
}
