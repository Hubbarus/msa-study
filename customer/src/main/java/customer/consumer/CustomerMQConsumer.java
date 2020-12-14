package customer.consumer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMQConsumer {

    private String lastMsg;

    public ResponseEntity getResponse() {
        return null;
    }

    public void recieveMessage(String msg) {
        lastMsg = msg;
    }
}
