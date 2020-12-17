package customer.service;

import customer.consumer.ResponseHolder;
import customer.model.Receipt;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("doPayment")
public class DoPaymentService implements JavaDelegate {

    @Autowired
    private ResponseHolder holder;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Receipt receipt = (Receipt) execution.getVariable("receipt");
        holder.setResponse(new ResponseEntity<>(receipt.getReceiptBody(), HttpStatus.OK));
    }
}
