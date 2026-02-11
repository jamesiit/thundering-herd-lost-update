package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_tbl")
public class Order {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "client_time")
    private String clientTime;

    @Column(name = "client_quantity")
    private int clientQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    public Order(long orderId, String userId, String clientTime, int clientQuantity, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.clientTime = clientTime;
        this.clientQuantity = clientQuantity;
        this.orderStatus = orderStatus;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientTime() {
        return clientTime;
    }

    public void setClientTime(String clientTime) {
        this.clientTime = clientTime;
    }

    public int getClientQuantity() {
        return clientQuantity;
    }

    public void setClientQuantity(int clientQuantity) {
        this.clientQuantity = clientQuantity;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Order() {
    }
}
