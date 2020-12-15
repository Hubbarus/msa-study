package customer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import customer.model.Receipt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseManager {

    public ResponseEntity<?> createResponse(String msg) {
        Receipt receipt;
        ObjectMapper mapper = new ObjectMapper();
        try {
             receipt = (Receipt) mapper.readValue(new String(msg), new TypeReference<Object>() {
            });
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Out of stock",HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }
}
