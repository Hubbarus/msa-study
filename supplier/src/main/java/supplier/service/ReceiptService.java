package supplier.service;

import org.springframework.stereotype.Service;
import supplier.model.Order;
import supplier.model.Product;
import supplier.model.Receipt;

import java.util.Random;
import java.util.UUID;

@Service
public class ReceiptService {

    private final Random random = new Random();

    public Receipt generateReceipt(Order order, String body) {

        Receipt receipt = new Receipt();
        receipt.setReceiptId(UUID.randomUUID());
        receipt.setTotalPrice(order.getProductList().stream()
                .map(Product::getPrice)
                .reduce(0, Integer::sum));
        receipt.setReceiptBody(body);

        return receipt;
    }

}
