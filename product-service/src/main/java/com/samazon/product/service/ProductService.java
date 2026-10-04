package com.samazon.product.service;

import com.samazon.product.model.Product;
import com.samazon.product.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getById(Long id) {
    	Optional<Product> optionalProduct = repository.findById(id);
		if (optionalProduct.isPresent()) {
			return optionalProduct.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID not found: " + id);
    }
}

    public Product create(Product product) {
        product.setProductId(null);
        return repository.save(product);
    }

    public Product update(Long id, Product product) {
        Product existingProduct = getById(id);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setStock(product.getStock());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setCurrentPrice(product.getCurrentPrice());
        return repository.save(existingProduct);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }
}