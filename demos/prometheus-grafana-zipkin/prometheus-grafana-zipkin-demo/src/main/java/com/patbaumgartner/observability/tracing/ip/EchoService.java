package com.patbaumgartner.observability.tracing.ip;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class EchoService {

	private final RestClient restClient;

	public EchoService(RestClient.Builder restClientBuilder) {
		this.restClient = restClientBuilder.baseUrl("https://echo.free.beeceptor.com").build();
	}

	public Echo echo() {
		return restClient.get().retrieve().body(Echo.class);
	}

}
