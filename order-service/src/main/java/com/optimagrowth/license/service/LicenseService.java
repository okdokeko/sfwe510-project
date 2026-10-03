package com.samazon.order.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.samazon.order.config.ServiceConfig;
import com.samazon.order.model.Order;
import com.samazon.order.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	MessageSource messages;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	ServiceConfig config;


	public Order getOrder(String orderId, String organizationId){
		Order order = orderRepository.findByOrganizationIdAndOrderId(organizationId, orderId);
		if (null == order) {
			throw new IllegalArgumentException(String.format(messages.getMessage("order.search.error.message", null, null),orderId, organizationId));	
		}
		return order.withComment(config.getProperty());
	}

	public Order createOrder(Order order){
		order.setOrderId(UUID.randomUUID().toString());
		orderRepository.save(order);

		return order.withComment(config.getProperty());
	}

	public Order updateOrder(Order order){
		orderRepository.save(order);

		return order.withComment(config.getProperty());
	}

	public String deleteOrder(String orderId){
		String responseMessage = null;
		Order order = new Order();
		order.setOrderId(orderId);
		orderRepository.delete(order);
		responseMessage = String.format(messages.getMessage("order.delete.message", null, null),orderId);
		return responseMessage;

	}
}
