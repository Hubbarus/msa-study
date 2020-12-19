package supplier.camundaDelegate;

import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;
import supplier.model.Order;
import supplier.model.Product;
import supplier.model.Receipt;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class CreateResponseServiceTest {

    @InjectMocks
    private CreateResponseService createResponseService;

    private Order getTestOrder(List<Product> productList) {
        Order order = new Order();
        order.setActivityId("activity_ID");
        order.setProductList(productList);
        return order;
    }

    @Test
    public void testCreateResponse() throws Exception {
        Product product = new Product();
        product.setQuantity(6);

        Order order = getTestOrder(Collections.singletonList(product));

        Map<String, Object> variableMap = new HashMap<>();
        variableMap.put("order", order);
        variableMap.put("body", "test");
        variableMap.put("isAvailable", 0);

        DelegateExecutionFake executionFake = new DelegateExecutionFake();
        executionFake.withBusinessKey("12345");
        executionFake.setVariables(variableMap);

        createResponseService.execute(executionFake);

        Receipt expectedReceipt = new Receipt();
        expectedReceipt.setReceiptId(UUID.randomUUID());
        expectedReceipt.setTotalPrice(order.getProductList().stream()
                .map(Product::getPrice)
                .reduce(0, Integer::sum));
        expectedReceipt.setReceiptBody("test");
        expectedReceipt.setAvailability(0);

        Receipt actualReceipt = (Receipt) executionFake.getVariable("receipt");

        assertEquals(expectedReceipt.getTotalPrice(), actualReceipt.getTotalPrice());
        assertEquals(expectedReceipt.getReceiptBody(), actualReceipt.getReceiptBody());
        assertEquals(expectedReceipt.getAvailability(), actualReceipt.getAvailability());
    }

}