package supplier.camundaDelegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import supplier.model.Receipt;
import supplier.processapi.OrderAdapter;

@Component("sendResponseService")
public class SendResponseService implements JavaDelegate {

    @Autowired
    private OrderAdapter orderAdapter;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String activityId = execution.getCurrentActivityId();
        Receipt receipt = (Receipt) execution.getVariable("receipt");

        orderAdapter.send(activityId, receipt);
    }

}
