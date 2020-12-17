package customer.controller;

import customer.consumer.ResponseHolder;
import customer.model.Order;
import customer.model.Product;
import customer.service.CamundaStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class ApiController {

    @Autowired
    private ResponseHolder holder;
    @Autowired
    private CamundaStartService camundaStartService;

    @PostMapping("/api/sendOrder")
    public ResponseEntity<?> sendOrder(@RequestBody Product[] productsToOrder) {

        Order order = new Order();
        order.setProductList(Arrays.asList(productsToOrder));
        order.setActivityId("activity_ID");

        camundaStartService.startCamundaRestProcess(order);

        return holder.getResponse();
    }
}
