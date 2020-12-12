package supplier.controller;

import customer.service.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final TestService testService;

    public Controller(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/rest")
    public ResponseEntity<String> getRest() {
        return new ResponseEntity<>(testService.getMsg(), HttpStatus.OK);
    }
}
