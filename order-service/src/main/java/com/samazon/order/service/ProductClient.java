package com.samazon.order.service;

import com.samazon.order.model.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

// for fetching product data
@Component
public class ProductClient {
    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductDto getProduct(Long productId) {
        try {
            return restTemplate.getForObject(
                    "http://product-service/samazon/products/{id}", ProductDto.class, productId);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No product found w Id: " + productId);
        }
    }
}