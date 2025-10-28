package com.patbaumgartner.observability.booking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfiguration {

	@Bean
	RestClient driverClient(@Value("${app.driver-service-base-url}") String baseUrl, RestClient.Builder builder) {
		return builder.baseUrl(baseUrl).build();
	}

}
