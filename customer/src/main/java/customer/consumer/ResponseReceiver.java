package customer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import customer.config.RabbitMQConfig;
import customer.model.Receipt;
import customer.service.CamundaStartService;
import customer.service.ResponseManager;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@EnableRabbit
@Component
public class ResponseReceiver {

    @Autowired
    private ResponseManager manager;

    @Autowired
    private CamundaStartService startService;

    private ResponseEntity response;

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

    public void setResponse(ResponseEntity response) {
        wasCreated = true;
        this.response = response;
    }
}
