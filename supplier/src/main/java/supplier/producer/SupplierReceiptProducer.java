package supplier.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import supplier.model.Receipt;

import java.io.IOException;
import java.io.StringWriter;

@Component
public class SupplierReceiptProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(Receipt receipt) {
        StringWriter writer = new StringWriter();
        try {
            objectMapper.writeValue(writer, receipt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend(rabbitTemplate.getExchange(), rabbitTemplate.getRoutingKey(), writer.toString());
    }

}
