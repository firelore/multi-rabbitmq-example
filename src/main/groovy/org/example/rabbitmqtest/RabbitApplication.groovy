package org.example.rabbitmqtest

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@SpringBootApplication
class RabbitApplication {

	static void main(String[] args) {
		SpringApplication.run(RabbitApplication, args)
	}
}
