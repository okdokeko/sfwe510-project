package com.samazon.order.controller;

import com.samazon.order.model.Order;
import com.samazon.order.service.OrderService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/samazon/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> findAll() {
        return service.getAllOrders();
    }

    @GetMapping(value = "/{id}")
    public Order findById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return service.create(order);
    }

    @PutMapping(value = "/{id}")
    public Order update(@PathVariable Long id, @RequestBody Order order) {
        return service.update(id, order);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}