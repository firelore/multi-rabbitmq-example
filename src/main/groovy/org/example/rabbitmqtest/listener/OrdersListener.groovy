package org.example.rabbitmqtest.listener

import groovy.util.logging.Slf4j
import org.example.rabbitmqtest.conditions.ConditionalOnOrdersRabbit
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Slf4j
@Component
@ConditionalOnOrdersRabbit
class OrdersListener {
    @RabbitListener(
            containerFactory = 'ordersContainer',
            bindings = @QueueBinding(
                    value = @Queue(name = "orders", durable = "true", declare = "true"),
                    exchange = @Exchange(name = 'orders-exchange', declare = "true")
            )
    )
    void handleMessage(Message message) {
        log.info("Received order message: {}", new String(message.body))
    }
}
