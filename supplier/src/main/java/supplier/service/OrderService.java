package supplier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplier.model.Order;
import supplier.model.Product;
import supplier.repository.InMemoryOrderRepository;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private InMemoryOrderRepository orderRepository;

    public boolean areProductsAvailable(Order order) {
        return order.getProductList().stream().anyMatch(product -> product.getQuantity() <= 5);
    }

    public Order getOrderById(String id) {
        return orderRepository.getById(id);
    }

}
