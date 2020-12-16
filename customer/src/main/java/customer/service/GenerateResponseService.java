package customer.service;

import customer.consumer.ResponseReceiver;
import customer.model.Receipt;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("generateResponse")
public class GenerateResponseService implements JavaDelegate {

    @Autowired
    private ResponseReceiver receiver;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        int isOk = (int) execution.getVariable("isOk");

        if (isOk == 0) {
            Receipt receipt = (Receipt) execution.getVariable("receipt");
            receiver.setResponse(new ResponseEntity<>(receipt.getReceiptBody(), HttpStatus.NOT_ACCEPTABLE));
        }
    }
}
