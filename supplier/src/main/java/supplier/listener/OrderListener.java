package supplier.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import supplier.model.Product;
import supplier.producer.SupplierReceiptProducer;
import supplier.repository.InMemoryProductRepository;
import supplier.service.ReceiptService;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SupplierReceiptProducer producer;

    @Autowired
    private InMemoryProductRepository inMemoryProductRepository;

    @Autowired
    private ReceiptService receiptService;

    @RabbitListener(queues = "${rabbitmq.customerQueue}")
    public void listen(String msg) {
        List<Product> productList = new ArrayList<>();
        try {
            productList = objectMapper.readValue(msg, new TypeReference<List<Product>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        inMemoryProductRepository.addAll(productList);
        producer.send(receiptService.generateReceipt());
    }

}
