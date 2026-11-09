package com.samazon.product.controller;

import com.samazon.product.model.Product;
import com.samazon.product.service.ProductService;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/samazon/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    @RolesAllowed({"ADMIN", "USER"})
    public List<Product> findAll() {
        return service.getAllProducts();
    }

    @GetMapping(value = "/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Product findById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }

    @PutMapping(value = "/{id}")
    @RolesAllowed("ADMIN")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return service.update(id, product);
    }

    @DeleteMapping(value = "/{id}")
    @RolesAllowed("ADMIN")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}