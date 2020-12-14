package supplier.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    @RabbitListener(queues = "${rabbitmq.customerQueue}")
    public void listen(byte[] msg) {
        System.out.println("____________HEREEEE!!!_____________ " + new String(msg));
    }

}
