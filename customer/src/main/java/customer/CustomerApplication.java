package customer;

import customer.consumer.CustomerMQConsumer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerApplication {

	public static final String QUEUE_NAME = "queue";
	public static final String TOPIC_EXCHANGE_NAME = "exchange";

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(TOPIC_EXCHANGE_NAME);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("app.api.#");
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
											 MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QUEUE_NAME);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(CustomerMQConsumer receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
}
