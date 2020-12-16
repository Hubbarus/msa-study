package supplier.camundaDelegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("orderFromSupplierService")
public class OrderFromSupplierService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("-----------------Nice, some shit is ordered--------------------");
    }
}
