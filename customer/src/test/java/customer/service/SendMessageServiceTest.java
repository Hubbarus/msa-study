package customer.service;

import customer.model.Order;
import customer.model.Product;
import customer.producer.CustomerMQProducer;
import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class SendMessageServiceTest {

    @Mock
    private CustomerMQProducer producer;

    @InjectMocks
    private SendMessageService service;

    private Order order;

    @Before
    public void setUp() {
        Product firstProduct = new Product();
        firstProduct.setName("First Product");
        firstProduct.setPrice(100);
        firstProduct.setQuantity(3);

        Product secondProduct = new Product();
        secondProduct.setName("Second Product");
        secondProduct.setPrice(20);
        secondProduct.setQuantity(5);

        List<Product> productList = new ArrayList<>();
        Collections.addAll(productList, firstProduct, secondProduct);

        order = new Order();
        order.setActivityId("activityID");
        order.setProductList(productList);
    }

    @Test
    public void successSendMessageTest() throws Exception {
        DelegateExecutionFake executionFake = new DelegateExecutionFake();
        executionFake.setVariable("order", order);

        service.execute(executionFake);

        verify(producer, only()).sendMessage(order);

        assertNotNull(executionFake.getVariable("wasSent"));
    }
}