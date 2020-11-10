package com.doctor.assistant.experiment.czy.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建RabbitMQ的配置类 RabbitConfig，用来配置队列、交换器、路由等高级信息。
 */
@Configuration
public class RabbitMQConfig {
    public static final String dataInputQueue = "newDataInputQueue";
    public static final String dispatchQueue = "calculateQueue";
    public static final String scheduleQueue = "scheduleQueue";
    public static final String directRoutingKey = "topic.message";
    public static final String topicRoutingKey = "topic.#";
    public static final String directQueue = "directQueue";
    public static final String topicQueue = "topicQueue";

    @Bean
    public Queue dataInputQueue() {
        return new Queue(dataInputQueue);
    }

    @Bean
    public Queue calculateQueue() {
        return new Queue(dispatchQueue);
    }

    @Bean
    public Queue scheduleQueue() {
        return new Queue(scheduleQueue);
    }

    /**
     * 以下 1个 Exchange、2个 Binding 和 2 个 Queue ，暂时未用
     * @return
     */
    @Bean
    TopicExchange exchange(){
        return new TopicExchange("exchange");
    }
    // Direct 交换机的绑定
    @Bean
    Binding bindingDirectExchangeMessage(Queue directQueue, TopicExchange exchange){
        return BindingBuilder.bind(directQueue).to(exchange).with(directRoutingKey);
    }
    // Topic 交换机的绑定
    @Bean
    Binding bindingTopicExchangeMessages(Queue topicQueue, TopicExchange exchange){
        return BindingBuilder.bind(topicQueue).to(exchange).with(topicRoutingKey);
    }
    @Bean
    public Queue directQueue() {
        return new Queue(directQueue);
    }
    @Bean
    public Queue topicQueue() {
        return new Queue(topicQueue);
    }
}
