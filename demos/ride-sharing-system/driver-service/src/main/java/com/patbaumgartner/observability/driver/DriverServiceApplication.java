package com.patbaumgartner.observability.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DriverServiceApplication {

	static void main(String[] args) {
		SpringApplication.run(DriverServiceApplication.class, args);
	}

	private DriverServiceApplication() {
	}

}
