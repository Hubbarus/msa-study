package customer.producer;

import customer.CustomerApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CustomerMQProducer {

    @Autowired
    private RabbitTemplate template;

    @PostConstruct
    public void init() {
        
    }

    public void sendMessage() {
        template.convertAndSend(CustomerApplication.TOPIC_EXCHANGE_NAME, "app.api.rmg", "order pls!");
    }
}
