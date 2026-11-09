package com.samazon.order.controller;

import com.samazon.order.model.Order;
import com.samazon.order.service.OrderService;
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
@RequestMapping("/samazon/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    @RolesAllowed({"ADMIN", "USER"})
    public List<Order> findAll() {
        return service.getAllOrders();
    }

    @GetMapping(value = "/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Order findById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public Order create(@RequestBody Order order) {
        return service.create(order);
    }

    @PutMapping(value = "/{id}")
    @RolesAllowed("ADMIN")
    public Order update(@PathVariable Long id, @RequestBody Order order) {
        return service.update(id, order);
    }

    @DeleteMapping(value = "/{id}")
    @RolesAllowed("ADMIN")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}