package org.example.rabbitmqtest

import org.example.rabbitmqtest.conditions.ConditionalOnProductsRabbit
import org.example.rabbitmqtest.conditions.ConditionalOnOrdersRabbit
import org.example.rabbitmqtest.conditions.OnAnyRabbitIntegration
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.amqp.CachingConnectionFactoryConfigurer
import org.springframework.boot.autoconfigure.amqp.DirectRabbitListenerContainerFactoryConfigurer
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionFactoryBeanConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Configuration

/**
 * A {@link RabbitAutoConfiguration} extension to allow for loading based on properties.
 * The original {@code RabbitAutoConfiguration} should be excluded so that it doesn't
 * run additionally.
 *
 * Each connection should have a nested configuration file here and be controlled
 * with a {@code ConditionalOn*} condition so that they can be disabled on startup
 * if the infrastructure is not ready for them.
 */
@AutoConfiguration
@Conditional(OnAnyRabbitIntegration)
class ConditionalRabbitAutoConfig extends RabbitAutoConfiguration {
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProductsRabbit
    class ProductsConfiguration {

        @Bean('productsConnectionFactory')
        CachingConnectionFactory connectionFactory(RabbitConnectionFactoryBeanConfigurer rabbitConnectionFactoryBeanConfigurer,
                                                   CachingConnectionFactoryConfigurer rabbitCachingConnectionFactoryConfigurer) {
            RabbitConnectionFactoryBean connectionFactoryBean = new RabbitConnectionFactoryBean()
            rabbitConnectionFactoryBeanConfigurer.configure(connectionFactoryBean)
            connectionFactoryBean.afterPropertiesSet()
            com.rabbitmq.client.ConnectionFactory connectionFactory = connectionFactoryBean.getObject()

            CachingConnectionFactory factory = new CachingConnectionFactory(connectionFactory)
            rabbitCachingConnectionFactoryConfigurer.configure(factory)
            factory.virtualHost = '/products'
            factory
        }

        @Bean('productsContainer')
        DirectRabbitListenerContainerFactory containerFactory(@Qualifier('productsConnectionFactory') CachingConnectionFactory connectionFactory,
                                                              DirectRabbitListenerContainerFactoryConfigurer configurer) {
            def factory = new DirectRabbitListenerContainerFactory()
            configurer.configure(factory, connectionFactory)
            factory
        }

        @Bean('productsContainer-admin')
        RabbitAdmin rabbitAdmin(@Qualifier('productsConnectionFactory') CachingConnectionFactory cf) {
            return new RabbitAdmin(cf)
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnOrdersRabbit
    class OrdersConfig {
        @Bean('ordersConnectionFactory')
        CachingConnectionFactory connectionFactory(RabbitConnectionFactoryBeanConfigurer rabbitConnectionFactoryBeanConfigurer,
                                                   CachingConnectionFactoryConfigurer rabbitCachingConnectionFactoryConfigurer) {
            RabbitConnectionFactoryBean connectionFactoryBean = new RabbitConnectionFactoryBean()
            rabbitConnectionFactoryBeanConfigurer.configure(connectionFactoryBean)
            connectionFactoryBean.afterPropertiesSet()
            com.rabbitmq.client.ConnectionFactory connectionFactory = connectionFactoryBean.getObject()

            CachingConnectionFactory factory = new CachingConnectionFactory(connectionFactory)
            rabbitCachingConnectionFactoryConfigurer.configure(factory)
            factory.virtualHost = '/orders'
            factory
        }

        @Bean('ordersContainer')
        DirectRabbitListenerContainerFactory containerFactory(@Qualifier('ordersConnectionFactory') CachingConnectionFactory connectionFactory,
                                                              DirectRabbitListenerContainerFactoryConfigurer configurer) {
            def factory = new DirectRabbitListenerContainerFactory()
            configurer.configure(factory, connectionFactory)
            factory
        }

        @Bean('ordersContainer-admin')
        RabbitAdmin rabbitAdmin(@Qualifier('ordersConnectionFactory') CachingConnectionFactory cf) {
            return new RabbitAdmin(cf)
        }
    }
}
