package customer.consumer;

import customer.config.RabbitMQConfig;
import customer.service.CamundaStartService;
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

    @Autowired
    private CamundaStartService startService;

    private ResponseEntity<?> response;

    private boolean wasCreated;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_RESPONSE_NAME)
    public void receive(String msg) {
        System.out.println("in Receive");
        startService.correlateAndForward(msg);
    }

    public ResponseEntity<?> getResponse() {
        while (!wasCreated) {
            System.out.println("Waiting for msg..................");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        wasCreated = false;
        return response;
    }

    public void setResponse(ResponseEntity<?> response) {
        wasCreated = true;
        this.response = response;
    }
}
