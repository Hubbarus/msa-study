package customer.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseManager {

    public ResponseEntity<?> createResponse(byte[] msg) {
        ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.OK);

        return response;
    }
}
