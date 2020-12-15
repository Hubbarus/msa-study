package supplier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplier.model.Product;
import supplier.model.Receipt;
import supplier.repository.InMemoryProductRepository;

import java.util.Random;
import java.util.UUID;

@Service
public class ReceiptService {

    private final Random random = new Random();

    @Autowired
    private InMemoryProductRepository productRepository;

    public Receipt generateReceipt() {

        Receipt receipt = new Receipt();
        receipt.setReceiptId(UUID.randomUUID());
        receipt.setTotalPrice(productRepository.getProductList().stream()
                .map(Product::getPrice)
                .reduce(0, Integer::sum));
        if (random.nextBoolean()) {
            receipt.setReceiptBody("Ok");
        } else {
            receipt.setReceiptBody("Out of stock");
        }

        return receipt;
    }

}
