package com.samazon.order.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
@Entity
@Table(name="orders")
public class Order extends RepresentationModel<Order> {

	@Id
	@Column(name = "order_id", nullable = false)
	private String orderId;
	private String description;
	@Column(name = "organization_id", nullable = false)
	private String organizationId;
	@Column(name = "product_name", nullable = false)
	private String productName;
	@Column(name = "order_type", nullable = false)
	private String orderType;
	@Column(name="comment")
	private String comment;

	public Order withComment(String comment){
		this.setComment(comment);
		return this;
	}
}