package supplier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplier.model.Order;
import supplier.repository.InMemoryOrderRepository;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {

    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Autowired
    private InMemoryOrderRepository orderRepository;

    public boolean areProductsAvailable(Order order) {
        return order.getProductList().stream().noneMatch(product -> product.getQuantity() > 5);
    }

    public String addOrder(Order order) {
        String orderId = String.valueOf(atomicInteger.getAndIncrement());
        orderRepository.add(orderId, order);
        return orderId;
    }

    public Order getOrderById(String id) {
        return orderRepository.getById(id);
    }

}
