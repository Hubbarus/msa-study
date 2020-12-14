package customer.controller;

import customer.consumer.CustomerMQConsumer;
import customer.producer.CustomerMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    private CustomerMQProducer producer;
    @Autowired
    private CustomerMQConsumer consumer;

    @GetMapping("/api/sendOrder")
    public ResponseEntity<?> sendOrder() {
        System.err.println("in endpoint");
        producer.sendMessage();
        return consumer.getResponse();
    }
}
