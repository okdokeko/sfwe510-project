package com.samazon.order.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.samazon.order.model.Order;
import com.samazon.order.service.OrderService;

@RestController
@RequestMapping(value="v1/organization/{organizationId}/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value="/{orderId}",method = RequestMethod.GET)
	public ResponseEntity<Order> getOrder( @PathVariable("organizationId") String organizationId,
			@PathVariable("orderId") String orderId) {
		
		Order order = orderService.getOrder(orderId, organizationId);
		order.add( 
				linkTo(methodOn(OrderController.class).getOrder(organizationId, order.getOrderId())).withSelfRel(),
				linkTo(methodOn(OrderController.class).createOrder(order)).withRel("createOrder"),
				linkTo(methodOn(OrderController.class).updateOrder(order)).withRel("updateOrder"),
				linkTo(methodOn(OrderController.class).deleteOrder(order.getOrderId())).withRel("deleteOrder")
		);
		
		return ResponseEntity.ok(order);
	}

	@PutMapping
	public ResponseEntity<Order> updateOrder(@RequestBody Order request) {
		return ResponseEntity.ok(orderService.updateOrder(request));
	}
	
	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody Order request) {
		return ResponseEntity.ok(orderService.createOrder(request));
	}

	@DeleteMapping(value="/{orderId}")
	public ResponseEntity<String> deleteOrder(@PathVariable("orderId") String orderId) {
		return ResponseEntity.ok(orderService.deleteOrder(orderId));
	}
}
