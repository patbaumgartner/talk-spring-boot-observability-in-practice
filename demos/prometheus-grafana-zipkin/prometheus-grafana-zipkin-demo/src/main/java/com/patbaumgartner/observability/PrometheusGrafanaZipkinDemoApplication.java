package com.patbaumgartner.observability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrometheusGrafanaZipkinDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrometheusGrafanaZipkinDemoApplication.class, args);
	}

}
