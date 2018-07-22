package com.dbecommerce.controller;

import com.dbecommerce.domain.Producer;
import com.dbecommerce.domain.Product;
import com.dbecommerce.service.ProducerService;
import com.dbecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("v1/producers")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Producer> getProducers() {
        return producerService.getAllProducers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Producer getProducer(@PathVariable("id") Long id) {
        return producerService.getProducer(id);
    }

    @RequestMapping(value = "/{id}/products", method = RequestMethod.GET)
    public List<Product> getProducerAllProducts(@PathVariable("id") Long id) {
        return productService.getProducerAllProducts(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createProducer(@RequestBody Producer producer) {
        producerService.saveProducer(producer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteProducer(@PathVariable("id") Long id) {
        producerService.deleteProducer(id);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public Producer updateProducer(@RequestBody Producer producer) {
        return producerService.saveProducer(producer);
    }
}
