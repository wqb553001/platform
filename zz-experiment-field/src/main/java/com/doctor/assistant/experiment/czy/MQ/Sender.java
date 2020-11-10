package com.doctor.assistant.experiment.czy.MQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * 通过注入 AmqpTemplate接口的实例来实现消息的发送，
 * AmqpTemplate接口定义了一套针对AMQP协议的基础操作。
 *
 * 在该生产者，我们会产生一个字符串，并发送到名为 hello的队列中。
 */
@Component
public class Sender {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息 到 指定 queue
     *
     * @param queue
     * @param message
     */
    public void sendMessage(String queue, String message){
        logger.info("发送消息： sender: queue - {} ，message - {}", queue, message);
        this.amqpTemplate.convertAndSend(queue, message);
//        this.amqpTemplate.convertAndSend("exchage","topic.message","topic_message");
//        this.amqpTemplate.convertAndSend("exchage","topic.messages","topic_messages");
    }

    /**
     *
     *
     * @param exchange      路由器
     * @param routingKey    路由方式： topic.message OR topic.messages
     * @param message       消息
     */
    public void sendMessage(String exchange, String routingKey, String message) {
        logger.info("senderTo : exchange : {}, routingKey : {}, message : {}", exchange, routingKey, message);
        this.amqpTemplate.convertAndSend(exchange, routingKey, message);
    }
}
