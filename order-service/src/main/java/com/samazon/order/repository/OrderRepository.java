package com.samazon.order.repository;

import com.samazon.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

// keeping format w product service w the Jpa repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}