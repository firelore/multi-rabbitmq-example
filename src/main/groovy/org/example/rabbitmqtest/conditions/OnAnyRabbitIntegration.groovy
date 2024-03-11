package org.example.rabbitmqtest.conditions


import org.springframework.boot.autoconfigure.condition.AnyNestedCondition

/**
 * Condition to see if at least one of the RabbitMQ configuration are loaded
 */
class OnAnyRabbitIntegration extends AnyNestedCondition {
    OnAnyRabbitIntegration() {
        super(ConfigurationPhase.PARSE_CONFIGURATION)
    }

    @ConditionalOnProductsRabbit
    static class ProductsRabbitEnabled {}

    @ConditionalOnOrdersRabbit
    static class OrdersRabbitEnabled {}
}
