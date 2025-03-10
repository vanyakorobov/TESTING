package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryOrderRepository implements OrderRepository {
    private Map<Integer, Order> orderDatabase = new HashMap<>();
    private int currentId = 1;

    @Override
    public int saveOrder(Order order) {
        int id = currentId++;
        order.setId(id);
        orderDatabase.put(id, order);
        return id;
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return Optional.ofNullable(orderDatabase.get(id));
    }
}

