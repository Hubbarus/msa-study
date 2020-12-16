package customer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import customer.model.Order;
import customer.model.Receipt;
import customer.producer.CustomerMQProducer;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CamundaStartService {

    private static final String PROCESS_KEY = "Process_customer";

    @Autowired
    private RuntimeService service;

    public void startCamundaRestProcess(Order order) {
        System.out.println("-------------------------------MSG from StartRestService-------------------------------");

        ProcessInstanceWithVariables orderList = service
                .createProcessInstanceByKey(PROCESS_KEY)
                .setVariable("order", order)
                .executeWithVariablesInReturn();


        System.out.println("---------------------------END of StartRestProcess--------------------------");
    }

    public void correlateAndForward(String msg) {
        Receipt receiptObject = getReceiptObject(msg);

        MessageCorrelationBuilder message = service.createMessageCorrelation("message");
        message.setVariable("isOk", receiptObject.getAvailability());
        message.setVariable("receipt", receiptObject);
        message.correlate();
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
}
