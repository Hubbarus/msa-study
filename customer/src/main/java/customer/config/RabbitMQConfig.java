package customer.config;

import customer.consumer.ResponseReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_ORDER_NAME = "orderQueue";
    public static final String QUEUE_RESPONSE_NAME = "responseQueue";
    public static final String ORDER_EXCHANGE = "orderExchange";
    public static final String RESPONSE_EXCHANGE = "responseExchange";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("host.docker.internal");
        connectionFactory.setUsername("admin");
        connectionFactory.setConnectionTimeout(1000);
        connectionFactory.setPassword("admin");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE);
    }

    @Bean
    public DirectExchange responseExchange() {
        return new DirectExchange(RESPONSE_EXCHANGE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(QUEUE_ORDER_NAME);
    }

    @Bean
    public Queue responseQueue() {
        return new Queue(QUEUE_RESPONSE_NAME);
    }

    @Bean
    public Binding orderBinding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(orderExchange())
                .with("customer.key.baz");
    }

    @Bean
    public Binding responseBinding() {
        return BindingBuilder
                .bind(responseQueue())
                .to(responseExchange())
                .with("supplier.key.baz");
    }

    @Bean
    MessageListenerAdapter listenerAdapter(ResponseReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receive");
    }


    @Bean
    public SimpleMessageListenerContainer messageListenerContainer1(MessageListenerAdapter adapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(QUEUE_RESPONSE_NAME);
        container.setMessageListener(adapter);
        return container;
    }

}
