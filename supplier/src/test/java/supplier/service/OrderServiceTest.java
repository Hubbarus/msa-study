package supplier.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import supplier.model.Order;
import supplier.model.Product;
import supplier.repository.InMemoryOrderRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @Mock
    private InMemoryOrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Captor
    private ArgumentCaptor<String> idCaptor;

    private Order getTestOrder(List<Product> productList) {
        Order order = new Order();
        order.setActivityId("activity_ID");
        order.setProductList(productList);
        return order;
    }

    @Test
    public void testAreProductsAvailableTrue() {
        Product product = new Product();
        product.setQuantity(4);

        assertTrue(orderService.areProductsAvailable(getTestOrder(Collections.singletonList(product))));
    }

    @Test
    public void testAreProductsAvailableFalse() {
        Product product = new Product();
        product.setQuantity(22);

        assertFalse(orderService.areProductsAvailable(getTestOrder(Collections.singletonList(product))));
    }

    @Test
    public void testAddOrder() {
        Order order = getTestOrder(Collections.singletonList(new Product()));
        String orderId = orderService.addOrder(order);

        verify(orderRepository, only()).add(idCaptor.capture(), eq(order));
        assertEquals(orderId, idCaptor.getValue());
    }

}