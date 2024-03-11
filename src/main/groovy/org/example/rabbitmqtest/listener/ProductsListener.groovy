package org.example.rabbitmqtest.listener

import groovy.util.logging.Slf4j
import org.example.rabbitmqtest.conditions.ConditionalOnProductsRabbit
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Slf4j
@Component
@ConditionalOnProductsRabbit
class ProductsListener {
    @RabbitListener(
            containerFactory = 'productsContainer',
            bindings = @QueueBinding(
                    value = @Queue(name = "products", durable = "true", declare = "true"),
                    exchange = @Exchange(name = 'products-exchange', declare = "true")
            )
    )
    void handleMessage(Message message) {
        log.info("Received product message: {}", new String(message.body))
    }
}
