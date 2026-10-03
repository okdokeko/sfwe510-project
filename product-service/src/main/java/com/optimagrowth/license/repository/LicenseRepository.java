package com.samazon.product.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.samazon.product.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product,String>  {
    public List<Product> findByOrganizationId(String organizationId);
    public Product findByOrganizationIdAndProductId(String organizationId,String productId);
}
