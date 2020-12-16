package supplier.camundaDelegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import supplier.service.OrderService;

@Component("checkAvailabilityService")
public class CheckAvailabilityService implements JavaDelegate {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String orderId = (String) execution.getVariable("orderId");
        if (!orderService.areProductsAvailable(orderService.getOrderById(orderId))) {
            execution.setVariable("isAvailable", 0);
            execution.setVariable("body", "Out of stock");
        } else {
            execution.setVariable("body", "Ok");
        }
        execution.setVariable("order", orderService.getOrderById(orderId));
    }

}
