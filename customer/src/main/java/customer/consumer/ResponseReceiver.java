package customer.consumer;

import customer.config.RabbitMQConfig;
import customer.service.CamundaStartService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class ResponseReceiver {

    @Autowired
    private CamundaStartService startService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_RESPONSE_NAME)
    public void receive(String msg) {
        startService.correlateAndForward(msg);
    }


}
