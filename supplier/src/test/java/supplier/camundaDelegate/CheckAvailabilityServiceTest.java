package supplier.camundaDelegate;

import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import supplier.model.Order;
import supplier.model.Product;
import supplier.service.OrderService;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CheckAvailabilityServiceTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private CheckAvailabilityService checkAvailabilityService;

    private Order getTestOrder(List<Product> productList) {
        Order order = new Order();
        order.setActivityId("activity_ID");
        order.setProductList(productList);
        return order;
    }

    @Test
    public void testCheckAvailabilityTrue() throws Exception {
        Product product = new Product();
        product.setQuantity(4);

        Order order = getTestOrder(Collections.singletonList(product));

        when(orderService.areProductsAvailable(eq(order))).thenReturn(true);
        when(orderService.getOrderById(anyString())).thenReturn(order);

        DelegateExecutionFake executionFake = new DelegateExecutionFake();
        executionFake.withBusinessKey("12345");
        executionFake.setVariable("orderId", "1");

        checkAvailabilityService.execute(executionFake);

        assertEquals( "Ok", executionFake.getVariable("body"));
        assertEquals( 1, executionFake.getVariable("isAvailable"));
    }

    @Test
    public void testCheckAvailabilityFalse() throws Exception {
        Product product = new Product();
        product.setQuantity(45);

        Order order = getTestOrder(Collections.singletonList(product));

        when(orderService.getOrderById(anyString())).thenReturn(order);
        when(orderService.areProductsAvailable(eq(order))).thenReturn(false);

        DelegateExecutionFake executionFake = new DelegateExecutionFake();
        executionFake.withBusinessKey("12345");
        executionFake.setVariable("orderId", "1");

        checkAvailabilityService.execute(executionFake);

        assertEquals( "Out of stock, need to order some shit", executionFake.getVariable("body"));
        assertEquals( 0, executionFake.getVariable("isAvailable"));
    }
}