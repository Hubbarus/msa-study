package customer.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {

    private String activityId;

    private List<Product> productList;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!Objects.equals(activityId, order.activityId)) return false;
        return Objects.equals(productList, order.productList);
    }

    @Override
    public int hashCode() {
        int result = activityId != null ? activityId.hashCode() : 0;
        result = 31 * result + (productList != null ? productList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "activityId='" + activityId + '\'' +
                ", productList=" + productList +
                '}';
    }

}
