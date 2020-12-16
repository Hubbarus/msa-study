package supplier.camundaDelegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import supplier.model.Order;
import supplier.processapi.OrderAdapter;
import supplier.service.ReceiptService;

@Component("sendResponseService")
public class SendResponseService implements JavaDelegate {

    @Autowired
    private OrderAdapter orderAdapter;

    @Autowired
    private ReceiptService receiptService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String activityId = execution.getProcessBusinessKey();
        orderAdapter.send(activityId, receiptService.generateReceipt(
                (Order) execution.getVariable("order"),
                (String) execution.getVariable("body"))
        );
    }

}
