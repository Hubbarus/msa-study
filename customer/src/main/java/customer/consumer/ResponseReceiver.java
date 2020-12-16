package customer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import customer.config.RabbitMQConfig;
import customer.model.Receipt;
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
    private RuntimeService runtimeService;

    private ResponseEntity response;

    private boolean wasCreated;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_RESPONSE_NAME)
    public void receive(String msg) {
        System.out.println("in Receive");

        correlateAndForwardResponseToBPMNService(msg);
    }

    private void correlateAndForwardResponseToBPMNService(String message) {
        System.out.println("----------------------------Correlating message------------------------");

        Receipt receipt = getReceiptObject(message);

        int areProductsAvailable = receipt.getAvailability();

        HashMap<String, Object> processKeys = new HashMap<>();
        processKeys.put("isOk", areProductsAvailable);
        processKeys.put("receipt", receipt);

        String activityId = receipt.getActivityId();

        runtimeService.correlateMessage("Message_0c5by40", activityId, processKeys);
    }

    private Receipt getReceiptObject(String message) {
        Receipt receipt = new Receipt();
        ObjectMapper mapper = new ObjectMapper();
        try {
            receipt = mapper.readValue(message, new TypeReference<Receipt>() {
            });
            System.out.println(receipt);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return receipt;
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
