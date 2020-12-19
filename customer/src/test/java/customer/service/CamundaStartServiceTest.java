package customer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import customer.model.Receipt;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.bpm.extension.mockito.message.MessageCorrelationBuilderMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.StringWriter;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CamundaStartServiceTest {

    private static final String MESSAGE = "message";

    @Mock
    private RuntimeService runtimeService;

    private MessageCorrelationBuilder builder;

    @InjectMocks
    private CamundaStartService service;

    @Before
    public void init() {
        builder = new MessageCorrelationBuilderMock().get();

        //when
        when(runtimeService.createMessageCorrelation(MESSAGE)).thenReturn(builder);
    }
    @Test
    public void correlateAndForwardMessageTest() throws IOException {
        //generating receipt
        Receipt receipt = new Receipt();
        receipt.setReceiptId(UUID.randomUUID());
        receipt.setActivityId("activityID");
        receipt.setAvailability(1);
        receipt.setReceiptBody("receiptBody");
        receipt.setTotalPrice(100);

        //generating message string
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, receipt);

        //executing
        service.correlateAndForward(writer.toString());

        //verifying
        verify(runtimeService, only()).createMessageCorrelation(anyString());
    }
}