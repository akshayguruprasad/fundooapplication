package com.indream.fundoo.configuration;

import org.springframework.amqp.core.AmqpTemplate;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.indream.fundoo.util.MailListener;

@Configuration
public class RabbitMqConfig {
	public static final String TOPICEXCHANGENAME = "DirectMessageExchange";

	static final String QUEUENAME = "EmailBuffer";
	public static final String ROUTING_KEY = "EmailKey";
	static final String LISTENERMETHOD = "sendEmail";

	@Bean
	Queue queue() {
		return new Queue(QUEUENAME, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(TOPICEXCHANGENAME);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MailListener receiver) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QUEUENAME);
		container.setMessageListener(new MessageListenerAdapter(receiver, LISTENERMETHOD));
		return container;
	}

	@Bean(name = "template")
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		return rabbitTemplate;
	}
}
