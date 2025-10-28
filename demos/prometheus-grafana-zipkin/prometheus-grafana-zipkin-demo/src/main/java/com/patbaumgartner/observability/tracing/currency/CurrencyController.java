package com.patbaumgartner.observability.tracing.currency;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tracing")
public class CurrencyController {

	private final CurrencyService currencyService;

	@GetMapping("/currencies")
	public List<Currency> getCurrencies() {
		return currencyService.findAllCurrencies();
	}

}
