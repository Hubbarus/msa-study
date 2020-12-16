package customer.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("generateResponse")
public class GenerateResponseService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String isOk = (String) execution.getVariable("isOk");

    }
}
