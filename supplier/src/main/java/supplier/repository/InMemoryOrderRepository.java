package supplier.repository;

import org.springframework.stereotype.Repository;
import supplier.model.Order;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryOrderRepository {

    private Map<String, Order> orderMap = new HashMap<>();

    public void add(String orderId, Order order) {
        orderMap.put(orderId, order);
    }

    public Order getById(String id) {
        return orderMap.get(id);
    }

}
