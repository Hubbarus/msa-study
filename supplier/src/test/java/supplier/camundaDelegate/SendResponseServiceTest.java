package supplier.camundaDelegate;

import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.junit4.SpringRunner;
import supplier.model.Receipt;
import supplier.processapi.OrderAdapter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class SendResponseServiceTest {

    @Mock
    private OrderAdapter orderAdapter;

    @InjectMocks
    private SendResponseService sendResponseService;

    @Captor
    private ArgumentCaptor<String> activityIdCaptor;

    @Test
    public void testSendResponse() throws Exception {
        Receipt receipt = new Receipt();

        DelegateExecutionFake executionFake = new DelegateExecutionFake();
        executionFake.withProcessBusinessKey("12345");
        executionFake.setVariable("receipt", receipt);

        sendResponseService.execute(executionFake);

        verify(orderAdapter, only()).send(activityIdCaptor.capture(), eq(receipt));

        assertEquals(activityIdCaptor.getValue(), executionFake.getProcessBusinessKey());
    }

}