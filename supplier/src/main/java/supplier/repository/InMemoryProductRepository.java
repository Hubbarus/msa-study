package supplier.repository;

import org.springframework.stereotype.Repository;
import supplier.model.Product;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryProductRepository {

    private final List<Product> productList = new ArrayList<>();

    public void addAll(List<Product> list) {
        productList.addAll(list);
    }

    public List<Product> getProductList() {
        return productList;
    }

}
