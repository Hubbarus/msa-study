package customer.service;

import customer.model.Order;
import customer.producer.CustomerMQProducer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sendMessage")
public class SendMessageService implements JavaDelegate {

    @Autowired
    private CustomerMQProducer producer;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("-----------------------MSG from execute SendMessageService------------------------");

        producer.sendMessage((Order) execution.getVariable("order"));

        execution.setVariable("wasSent", 1);
    }
}
