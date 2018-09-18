package com.dbecommerce.controller;

import com.dbecommerce.domain.dto.ProducerDto;
import com.dbecommerce.domain.dto.ProductDto;
import com.dbecommerce.mapper.ProducerMapper;
import com.dbecommerce.mapper.ProductMapper;
import com.dbecommerce.service.ProducerService;
import com.dbecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
    @Autowired
    private ProducerMapper producerMapper;
    @Autowired
    private ProductMapper productMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<ProducerDto> getProducers() {
        return producerMapper.mapToListProducerDto(producerService.getAllProducers());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProducerDto getProducer(@PathVariable("id") Long id) {
        return producerMapper.mapToProducerDto(producerService.getProducer(id));
    }

    @RequestMapping(value = "/{id}/products", method = RequestMethod.GET)
    public List<ProductDto> getProducerAllProducts(@PathVariable("id") Long id) {
        return productMapper.mapToListProductDto(productService.getProducerAllProducts(id));
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void createProducer(@RequestBody ProducerDto producerDto) {
        producerService.saveProducer(producerMapper.mapToProducer(producerDto));
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteProducer(@PathVariable("id") Long id) {
        producerService.deleteProducer(id);
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    public ProducerDto updateProducer(@RequestBody ProducerDto producerDto) {
        return producerMapper.mapToProducerDto(producerService.saveProducer(producerMapper.mapToProducer(producerDto)));
    }
}
