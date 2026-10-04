package com.samazon.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    
    // Adding relationship to order
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order orderId;
    private Long productId;
    private Integer quantity;
    @Column(precision = 10, scale = 2)
    private BigDecimal snapshotPrice;

    // Specifying relation to order
    @JsonProperty(value = "orderId", access = JsonProperty.Access.READ_ONLY)
    public Long getOrderId() {
        return orderId == null ? null : orderId.getOrderId();
    }
}