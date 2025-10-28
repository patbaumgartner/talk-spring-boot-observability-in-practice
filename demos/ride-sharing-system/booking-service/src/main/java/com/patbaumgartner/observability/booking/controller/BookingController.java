package com.patbaumgartner.observability.booking.controller;

import com.patbaumgartner.observability.booking.dto.BookingRequest;
import com.patbaumgartner.observability.booking.dto.BookingResponse;
import com.patbaumgartner.observability.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/bookings")
class BookingController {

	private final BookingService service;

	BookingController(BookingService service) {
		this.service = service;
	}

	@PostMapping
	ResponseEntity<BookingResponse> create(@Valid @RequestBody BookingRequest req) {
		LOGGER.atInfo()
			.addArgument(() -> req.pickupLat())
			.addArgument(() -> req.pickupLon())
			.log("Received booking request for pickup location: lat={}, lon={}");
		BookingResponse response = service.createBooking(req);
		LOGGER.atInfo()
			.addArgument(() -> response.status())
			.addArgument(() -> response.driverId())
			.addArgument(() -> response.driverName())
			.addArgument(() -> response.etaMinutes())
			.log("Booking created successfully: status={}, driverId={}, driverName={}, eta={} min");
		return ResponseEntity.ok(response);
	}

}
