package org.example;

import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String processOrder(Order order) {
        try {
            int orderId = orderRepository.saveOrder(order);
            return "Order processed successfully with ID: " + orderId;
        } catch (Exception e) {
            return "Order processing failed";
        }
    }

    public double calculateTotal(Order order) {
        if (order == null) {
            return 0.0;
        }

        int quantity = order.getQuantity();
        double unitPrice = order.getUnitPrice();

        return order.getTotalPrice();
    }
}

