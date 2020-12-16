package customer.service;

import customer.model.Order;
import customer.producer.CustomerMQProducer;
import org.camunda.bpm.engine.RuntimeService;
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

    public void startCamundaProcess(Order order) {
        System.out.println("-------------------------------MSG from StartService-------------------------------");

        ProcessInstanceWithVariables orderList = service
                .createProcessInstanceByKey(PROCESS_KEY)
                .setVariable("order", order)
                .executeWithVariablesInReturn();


        System.out.println("---------------------------END of start process--------------------------");
    }
}
