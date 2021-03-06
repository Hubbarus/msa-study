package supplier.camundaDelegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import supplier.model.Order;
import supplier.service.OrderService;

@Component("checkAvailabilityService")
public class CheckAvailabilityService implements JavaDelegate {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String orderId = (String) execution.getVariable("orderId");
        Order order = orderService.getOrderById(orderId);
        if (orderService.areProductsAvailable(order)) {
            execution.setVariable("body", "Ok");
            execution.setVariable("isAvailable", 1);
        } else {
            execution.setVariable("body", "Out of stock, need to order some shit");
            execution.setVariable("isAvailable", 0);
        }
        execution.setVariable("order", orderService.getOrderById(orderId));
    }

}
