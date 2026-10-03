package com.samazon.product.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.samazon.product.config.ServiceConfig;
import com.samazon.product.model.Product;
import com.samazon.product.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	MessageSource messages;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	ServiceConfig config;


	public Product getProduct(String productId, String organizationId){
		Product product = productRepository.findByOrganizationIdAndProductId(organizationId, productId);
		if (null == product) {
			throw new IllegalArgumentException(String.format(messages.getMessage("product.search.error.message", null, null),productId, organizationId));	
		}
		return product.withComment(config.getProperty());
	}

	public Product createProduct(Product product){
		product.setProductId(UUID.randomUUID().toString());
		productRepository.save(product);

		return product.withComment(config.getProperty());
	}

	public Product updateProduct(Product product){
		productRepository.save(product);

		return product.withComment(config.getProperty());
	}

	public String deleteProduct(String productId){
		String responseMessage = null;
		Product product = new Product();
		product.setProductId(productId);
		productRepository.delete(product);
		responseMessage = String.format(messages.getMessage("product.delete.message", null, null),productId);
		return responseMessage;

	}
}
