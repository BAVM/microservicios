package com.example.orderservice.controller;

import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.client.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            // Verificar si el usuario existe antes de crear el pedido
            if (!userServiceClient.userExists(order.getUserId())) {
                return ResponseEntity.badRequest()
                        .body("{\"error\": \"Usuario no encontrado con ID: " + order.getUserId() + "\"}");
            }

            // Establecer la fecha actual si no viene en el request
            if (order.getOrderDate() == null) {
                order.setOrderDate(java.time.LocalDateTime.now());
            }

            Order savedOrder = orderRepository.save(order);
            return ResponseEntity.ok(savedOrder);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("{\"error\": \"Error interno del servidor: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @GetMapping("/{id}/user-info")
    public ResponseEntity<?> getOrderWithUserInfo(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Order orderObj = order.get();
        String userEmail = userServiceClient.getUserEmail(orderObj.getUserId());

        OrderWithUserInfo response = new OrderWithUserInfo(
                orderObj.getId(),
                orderObj.getUserId(),
                userEmail,
                orderObj.getProductName(),
                orderObj.getAmount(),
                orderObj.getOrderDate()
        );

        return ResponseEntity.ok(response);
    }

    private static class OrderWithUserInfo {
        private Long orderId;
        private Long userId;
        private String userEmail;
        private String productName;
        private Double amount;
        private String orderDate;

        public OrderWithUserInfo(Long orderId, Long userId, String userEmail,
                                 String productName, Double amount, java.time.LocalDateTime orderDate) {
            this.orderId = orderId;
            this.userId = userId;
            this.userEmail = userEmail;
            this.productName = productName;
            this.amount = amount;
            this.orderDate = orderDate.toString();
        }

        public Long getOrderId()
        { return orderId; }

        public Long getUserId()
        { return userId; }

        public String getUserEmail()
        { return userEmail; }

        public String getProductName()
        { return productName; }

        public Double getAmount()
        { return amount; }

        public String getOrderDate()
        { return orderDate; }
    }
}