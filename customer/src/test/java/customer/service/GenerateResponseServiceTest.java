package customer.service;

import customer.consumer.ResponseHolder;
import customer.model.Receipt;
import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class GenerateResponseServiceTest {

    @Mock
    private ResponseHolder holder;

    @InjectMocks
    private GenerateResponseService service;

    @Captor
    private ArgumentCaptor<ResponseEntity<?>> captor;

    private Receipt receipt;

    @Before
    public void setUp() {
        // Setting receipt
        receipt = new Receipt();
        receipt.setReceiptId(UUID.randomUUID());
        receipt.setActivityId("activityID");
        receipt.setAvailability(1);
        receipt.setReceiptBody("receiptBody");
        receipt.setTotalPrice(100);
    }

    @Test
    public void forwardToDoPaymentServiceAppliedTest() throws Exception {
        // Setting variables to execution
        DelegateExecutionFake executionFake = new DelegateExecutionFake();
        executionFake.setVariable("isOk", 1);

        service.execute(executionFake);

        verify(holder, never()).setResponse(any(ResponseEntity.class));
    }

    @Test
    public void negativeReceiptReceivedTest() throws Exception {
        // Setting variables to execution
        DelegateExecutionFake executionFake = new DelegateExecutionFake();
        executionFake.setVariable("receipt", receipt);
        executionFake.setVariable("isOk", 0);

        service.execute(executionFake);

        verify(holder, only()).setResponse(captor.capture());

        assertEquals(captor.getValue().getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
        assertEquals(captor.getValue().getBody(), receipt.getReceiptBody());
    }
}