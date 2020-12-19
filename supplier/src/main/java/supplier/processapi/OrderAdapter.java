package supplier.processapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import supplier.model.Order;
import supplier.model.Receipt;
import supplier.service.OrderService;

import java.io.IOException;
import java.io.StringWriter;

@Component
public class OrderAdapter {

    private static final String ORDER_PROCESS_KEY = "Process_supplier";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = "${rabbitmq.customerQueue}")
    public void start(String msg) throws JsonProcessingException {
        startCamundaProcess(msg);
    }

    private void startCamundaProcess(String message) throws JsonProcessingException {
        Order order = objectMapper.readValue(message, Order.class);
        runtimeService.createProcessInstanceByKey(ORDER_PROCESS_KEY)
                    .setVariable("orderId", orderService.addOrder(order))
                    .executeWithVariablesInReturn();

    }

    public void send(String activityId, Receipt receipt) {
        receipt.setActivityId(activityId);
        StringWriter writer = new StringWriter();
        try {
            objectMapper.writeValue(writer, receipt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend(rabbitTemplate.getExchange(), rabbitTemplate.getRoutingKey(), writer.toString());
    }

}
