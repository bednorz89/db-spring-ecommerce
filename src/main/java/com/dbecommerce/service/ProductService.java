package com.dbecommerce.service;

import com.dbecommerce.domain.Product;
import com.dbecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProducerService producerService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product, Long id) {
        product.setProducer(producerService.getProducer(id));
        return productRepository.save(product);
    }

    public Product getProduct(Long id) {
        return productRepository.findOne(id);
    }

    public Product updateProduct(Product product) {
        product.setProducer(productRepository.findOne(product.getId()).getProducer());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }

    public List<Product> getProducerAllProducts(Long id) {
        return productRepository.findAllByProducer(producerService.getProducer(id));
    }


}
