package com.patbaumgartner.observability.tracing.ip;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tracing")
@RequiredArgsConstructor
public class EchoController {

	private final EchoService echoService;

	@GetMapping("/echo")
	public Echo echo() {
		return echoService.echo();
	}

}
