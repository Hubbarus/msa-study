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
        System.out.println("Creating response.......");
        Receipt receipt = new Receipt();
        ObjectMapper mapper = new ObjectMapper();
        try {
             receipt = mapper.readValue(msg, new TypeReference<Receipt>() {
            });
            System.out.println(receipt);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receiptBody = receipt.getReceiptBody();
        return receiptBody.equals("Ok") ?
                new ResponseEntity<>(receiptBody, HttpStatus.OK)
                : new ResponseEntity<>(receiptBody, HttpStatus.NOT_IMPLEMENTED);
    }
}
