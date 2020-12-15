package supplier.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Receipt implements Serializable {

    private UUID receiptId;

    private String receiptBody;

    private int totalPrice;

    public Receipt() {
    }

    public UUID getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(UUID receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiptBody() {
        return receiptBody;
    }

    public void setReceiptBody(String receiptBody) {
        this.receiptBody = receiptBody;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receipt receipt = (Receipt) o;

        if (totalPrice != receipt.totalPrice) return false;
        if (!Objects.equals(receiptId, receipt.receiptId)) return false;
        return Objects.equals(receiptBody, receipt.receiptBody);
    }

    @Override
    public int hashCode() {
        int result = receiptId != null ? receiptId.hashCode() : 0;
        result = 31 * result + (receiptBody != null ? receiptBody.hashCode() : 0);
        result = 31 * result + totalPrice;
        return result;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId=" + receiptId +
                ", receiptBody='" + receiptBody + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
