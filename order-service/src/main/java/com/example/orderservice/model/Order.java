package com.example.orderservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    public Order() {}

    public Order(Long userId, String productName, Double amount) {
        this.userId = userId;
        this.productName = productName;
        this.amount = amount;
        this.orderDate = LocalDateTime.now();
    }

    // Genaramos los Getters and Setters

    public Long getId()
    { return id; }
    public void setId(Long id)
    { this.id = id; }

    public Long getUserId()
    { return userId; }
    public void setUserId(Long userId)
    { this.userId = userId; }

    public String getProductName()
    { return productName; }
    public void setProductName(String productName)
    { this.productName = productName; }

    public Double getAmount()
    { return amount; }
    public void setAmount(Double amount)
    { this.amount = amount; }

    public LocalDateTime getOrderDate()
    { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate)
    { this.orderDate = orderDate; }
}