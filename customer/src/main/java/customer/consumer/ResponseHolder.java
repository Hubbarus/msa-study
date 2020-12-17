package customer.consumer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseHolder {

    private ResponseEntity<?> response;
    private boolean wasCreated;

    public ResponseEntity<?> getResponse() {
        while (!wasCreated) {
            System.out.println("Waiting for msg..................");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        wasCreated = false;
        return response;
    }

    public void setResponse(ResponseEntity<?> response) {
        wasCreated = true;
        this.response = response;
    }
}
