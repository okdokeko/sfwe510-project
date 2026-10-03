package com.samazon.order.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.samazon.order.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order,String>  {
    public List<Order> findByOrganizationId(String organizationId);
    public Order findByOrganizationIdAndOrderId(String organizationId,String orderId);
}
