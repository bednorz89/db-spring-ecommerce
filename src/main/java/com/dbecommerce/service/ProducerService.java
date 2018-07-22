package com.dbecommerce.service;

import com.dbecommerce.domain.Producer;
import com.dbecommerce.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {

    @Autowired
    private ProducerRepository producerRepository;

    public List<Producer> getAllProducers() {
        return producerRepository.findAll();
    }

    public Producer getProducer(Long id) {
        return producerRepository.findOne(id);
    }

    public Producer saveProducer(Producer producer) {
        return producerRepository.save(producer);
    }

    public void deleteProducer(Long id) {
        producerRepository.delete(id);
    }

}
