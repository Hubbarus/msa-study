package customer.consumer;

import customer.config.RabbitMQConfig;
import customer.service.ResponseManager;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class ResponseReceiver {

    @Autowired
    private ResponseManager manager;

    private ResponseEntity<?> response;
    private boolean wasReceived;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_RESPONSE_NAME)
    public void receive(String msg) {
        System.out.println("in Receive");
        wasReceived = true;
        response = manager.createResponse(msg);
    }

    public ResponseEntity<?> getResponse() {
        while (!wasReceived) {
            System.out.println("Waiting for msg..................");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        wasReceived = false;
        return response;
    }
}
