package customer.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import customer.config.RabbitMQConfig;
import customer.model.Product;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Component
public class CustomerMQProducer {

    @Autowired
    private RabbitTemplate template;

    public void sendMessage(List<Product> productsToOrder) {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();

        try {
            mapper.writeValue(writer, productsToOrder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        template.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, "customer.key.baz", writer.toString());
        System.err.println("in SendMessage");
    }
}
