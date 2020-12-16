package supplier.camundaDelegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import supplier.model.Order;
import supplier.model.Product;
import supplier.model.Receipt;

import java.util.UUID;

@Component("createResponseService")
public class CreateResponseService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        Order order = (Order) execution.getVariable("order");
        String body = (String) execution.getVariable("body");

        Receipt receipt = new Receipt();
        receipt.setReceiptId(UUID.randomUUID());
        receipt.setTotalPrice(order.getProductList().stream()
                .map(Product::getPrice)
                .reduce(0, Integer::sum));
        receipt.setReceiptBody(body);
        receipt.setAvailability((int) execution.getVariable("isAvailable"));

        execution.setVariable("receipt", receipt);

    }
}
