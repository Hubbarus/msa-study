package customer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import customer.model.Order;
import customer.model.Receipt;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamundaStartService {

    private static final String PROCESS_KEY = "Process_customer";

    @Autowired
    private RuntimeService service;

    public void startCamundaRestProcess(Order order) {
        ProcessInstanceWithVariables orderList = service
                .createProcessInstanceByKey(PROCESS_KEY)
                .setVariable("order", order)
                .executeWithVariablesInReturn();
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
            receipt = mapper.readValue(message, Receipt.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return receipt;
    }
}
