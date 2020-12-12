package customer.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public String getMsg() {
        return "This is message from TestService";
    }
}
