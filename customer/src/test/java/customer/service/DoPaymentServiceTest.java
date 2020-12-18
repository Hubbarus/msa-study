package customer.service;

import customer.consumer.ResponseHolder;
import customer.model.Receipt;
import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class DoPaymentServiceTest {

    @Mock private ResponseHolder holder;

    @InjectMocks private DoPaymentService service;

    @Captor
    private ArgumentCaptor<ResponseEntity<?>> captor;

    @Test
    public void successPaymentTest() throws Exception {
        // Setting receipt
        Receipt receipt = new Receipt();
        receipt.setReceiptId(UUID.randomUUID());
        receipt.setActivityId("activityID");
        receipt.setAvailability(1);
        receipt.setReceiptBody("receiptBody");
        receipt.setTotalPrice(100);

        // Setting variables to execution
        DelegateExecutionFake executionFake = new DelegateExecutionFake();
        executionFake.setVariable("receipt", receipt);

        //executing service
        service.execute(executionFake);

        //verifying
        verify(holder, only())
                .setResponse(captor.capture());

        //assert
        assertEquals(captor.getValue().getStatusCode(), HttpStatus.OK);
        assertEquals(captor.getValue().getBody(), receipt.getReceiptBody());
    }

}