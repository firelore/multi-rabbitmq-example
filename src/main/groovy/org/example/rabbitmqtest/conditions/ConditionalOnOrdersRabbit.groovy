package org.example.rabbitmqtest.conditions

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * Conditional annotation to see if the Orders RabbitMQ configuration should
 * be loaded. Other beans that depend on the Orders RabbitMQ configuration,
 * such as listeners, can also use this so that they will not be loaded when
 * this feature is disabled.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target([ ElementType.TYPE, ElementType.METHOD ])
@Documented
@ConditionalOnProperty(name = 'rabbit.orders.enabled', havingValue = 'true', matchIfMissing = true)
@interface ConditionalOnOrdersRabbit {

}