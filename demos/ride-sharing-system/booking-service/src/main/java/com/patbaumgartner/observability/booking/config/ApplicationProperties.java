package com.patbaumgartner.observability.booking.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record ApplicationProperties(String driverServiceBaseUrl) {
}
