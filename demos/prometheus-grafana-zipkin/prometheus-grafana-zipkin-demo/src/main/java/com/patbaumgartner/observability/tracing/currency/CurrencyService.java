package com.patbaumgartner.observability.tracing.currency;

import java.util.List;


import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurrencyService {

	private final CurrencyRepository currencyRepository;

	@PostConstruct
	public void init() {
		List<Country> countries = List.of(new Country(null, "Switzerland"), new Country(null, "Liechtenstein"));
		currencyRepository.save(new Currency(null, "Swiss Franc", "CHF", countries));
	}

	public List<Currency> findAllCurrencies() {
		return currencyRepository.findAll();
	}

}
