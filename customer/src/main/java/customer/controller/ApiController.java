package customer.controller;

import customer.consumer.ResponseReceiver;
import customer.model.Product;
import customer.producer.CustomerMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class ApiController {

    @Autowired
    private CustomerMQProducer producer;
    @Autowired
    private ResponseReceiver receiver;

    @PostMapping("/api/sendOrder")
    public ResponseEntity<?> sendOrder(@RequestBody Product[] productsToOrder) {
        System.out.println(Arrays.asList(productsToOrder));

        producer.sendMessage(Arrays.asList(productsToOrder));

        return receiver.getResponse();
    }
}
