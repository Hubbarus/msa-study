package customer.entity;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    private String name;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return quantity == product.quantity && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
