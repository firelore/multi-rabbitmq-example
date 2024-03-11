package org.example.rabbitmqtest.conditions

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

import java.lang.annotation.*

/**
 * Conditional annotation to see if the Products RabbitMQ configuration should
 * be loaded. Other beans that depend on the Products RabbitMQ configuration,
 * such as listeners, can also use this so that they will not be loaded when
 * this feature is disabled.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target([ ElementType.TYPE, ElementType.METHOD ])
@Documented
@ConditionalOnProperty(name = 'rabbit.products.enabled', havingValue = 'true', matchIfMissing = true)
@interface ConditionalOnProductsRabbit {

}