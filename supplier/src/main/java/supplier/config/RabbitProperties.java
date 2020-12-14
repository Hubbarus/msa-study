package supplier.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitProperties {

    @Value(value = "${rabbitmq.host}")
    private String host;

    @Value(value = "${rabbitmq.port}")
    private int port;

    @Value(value = "${rabbitmq.user}")
    private String user;

    @Value(value = "${rabbitmq.password}")
    private String password;

    @Value(value = "${rabbitmq.customerQueue}")
    private String customerQueue;

    @Value(value = "${rabbitmq.supplierQueue}")
    private String supplierQueue;

    @Value(value = "${rabbitmq.customerExchange}")
    private String customerExchange;

    @Value(value = "${rabbitmq.supplierExchange}")
    private String supplierExchange;

    @Value(value = "${rabbitmq.customerRoutingKey}")
    private String customerRoutingKey;

    @Value(value = "${rabbitmq.supplierRoutingKey}")
    private String supplierRoutingKey;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getCustomerExchange() {
        return customerExchange;
    }

    public String getSupplierExchange() {
        return supplierExchange;
    }

    public String getCustomerQueue() {
        return customerQueue;
    }

    public String getSupplierQueue() {
        return supplierQueue;
    }

    public String getCustomerRoutingKey() {
        return customerRoutingKey;
    }

    public String getSupplierRoutingKey() {
        return supplierRoutingKey;
    }

}
