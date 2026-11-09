package com.samazon.order.service;

import com.samazon.order.model.Order;
import com.samazon.order.model.OrderItem;
import com.samazon.order.model.OrderStatus;
import com.samazon.order.model.ProductDto;
import com.samazon.order.repository.OrderRepository;
import com.samazon.order.service.ProductClient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final ProductClient productClient; // Add the actual integration

    public OrderService(OrderRepository repository, ProductClient productClient) {
        this.repository = repository;
        this.productClient = productClient;
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order getById(Long id) {
        Optional<Order> order = repository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with ID not found: " + id);
        }
    }

	// create order, set defaults in case of nulls
    public Order create(Order order) {
        order.setOrderId(null);
        order.setDate(LocalDateTime.now());
        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.PENDING);
        }
        for (OrderItem item : order.getItems()) {
            item.setItemId(null);
            item.setOrderId(order);
            ProductDto product = productClient.getProduct(item.getProductId());
            if (product.getStock() == null || product.getStock() < item.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, // secret easter egg :)
                        "Out of stock; Product: " + item.getProductId());
            }
            item.setSnapshotPrice(product.getCurrentPrice());
        }
        order.setTotalPrice(calculateTotal(order));
        return repository.save(order);
    }

    public Order update(Long id, Order updated) {
        Order existingOrder = getById(id);
        existingOrder.setCustomerId(updated.getCustomerId());
        // status update is optional
		if (updated.getStatus() != null) {
            existingOrder.setStatus(updated.getStatus());
        }
        existingOrder.getItems().clear();
        for (OrderItem item : updated.getItems()) {
            item.setItemId(null);
            item.setOrderId(existingOrder);
            existingOrder.getItems().add(item); // add item to list
        }
		// recalculate the total
        existingOrder.setTotalPrice(calculateTotal(existingOrder));
        return repository.save(existingOrder);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }

	// small helper function to get the total price 
    private BigDecimal calculateTotal(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : order.getItems()) {
            total = total.add(item.getSnapshotPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }
}