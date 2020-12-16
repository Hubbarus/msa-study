package supplier.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import supplier.processapi.OrderAdapter;

@Configuration
public class RabbitConfig {

    @Autowired
    private RabbitProperties rabbitProperties;

    @Bean
    public Queue supplierQueue() {
        return new Queue(rabbitProperties.getSupplierQueue());
    }

    @Bean
    public Queue customerQueue() {
        return new Queue(rabbitProperties.getCustomerQueue());
    }

    @Bean
    public DirectExchange directCustomerExchange() {
        return new DirectExchange(rabbitProperties.getCustomerExchange());
    }

    @Bean
    public DirectExchange directSupplierExchange() {
        return new DirectExchange(rabbitProperties.getSupplierExchange());
    }

    @Bean
    public Binding supplierBinding() {
        return BindingBuilder
                .bind(supplierQueue())
                .to(directSupplierExchange())
                .with(rabbitProperties.getSupplierRoutingKey());
    }

    @Bean
    public Binding customerBinding() {
        return BindingBuilder
                .bind(customerQueue())
                .to(directCustomerExchange())
                .with(rabbitProperties.getCustomerRoutingKey());
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUser());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        return connectionFactory;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(OrderAdapter listener) {
        return new MessageListenerAdapter(listener, "start");
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(MessageListenerAdapter messageListenerAdapter) {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setQueueNames(rabbitProperties.getCustomerQueue());
        messageListenerContainer.setConnectionFactory(connectionFactory());
        messageListenerContainer.setMessageListener(messageListenerAdapter);
        return messageListenerContainer;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory());
        rabbitTemplate.setExchange(rabbitProperties.getSupplierExchange());
        rabbitTemplate.setRoutingKey(rabbitProperties.getSupplierRoutingKey());
        return rabbitTemplate;
    }

}
