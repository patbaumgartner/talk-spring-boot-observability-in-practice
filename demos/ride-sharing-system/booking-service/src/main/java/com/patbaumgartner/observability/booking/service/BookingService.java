package com.patbaumgartner.observability.booking.service;

import com.patbaumgartner.observability.booking.dto.BookingRequest;
import com.patbaumgartner.observability.booking.dto.BookingResponse;
import com.patbaumgartner.observability.booking.dto.NearestDriverResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Slf4j
@Service
public class BookingService {

	private final RestClient driverClient;

	public BookingService(RestClient driverClient) {
		this.driverClient = driverClient;
	}

	public BookingResponse createBooking(BookingRequest req) {
		LOGGER.atDebug()
			.addArgument(() -> req.pickupLat())
			.addArgument(() -> req.pickupLon())
			.log("Creating booking for customer at pickup location: lat={}, lon={}");

		// 1) find nearest driver
		LOGGER.debug("Searching for nearest available driver");
		NearestDriverResponse nearest = driverClient.post()
			.uri("/api/drivers/nearest")
			.body(Map.of("latitude", req.pickupLat(), "longitude", req.pickupLon()))
			.retrieve()
			.body(NearestDriverResponse.class);

		LOGGER.atInfo()
			.addArgument(() -> nearest.driverId())
			.addArgument(() -> nearest.name())
			.addArgument(() -> nearest.distanceKm())
			.addArgument(() -> nearest.rating())
			.log("Found nearest driver: id={}, name={}, distance={} km, rating={}");

		// 2) mark driver BUSY (fire-and-forget; in real life, wrap in saga/tx)
		LOGGER.atDebug().addArgument(() -> nearest.driverId()).log("Marking driver {} as BUSY");
		driverClient.patch()
			.uri("/api/drivers/{id}/status", nearest.driverId())
			.body(Map.of("status", "BUSY"))
			.retrieve()
			.toBodilessEntity();

		LOGGER.atDebug().addArgument(() -> nearest.driverId()).log("Driver {} marked as BUSY successfully");

		// Simple ETA heuristic: 2 min per km (cap 10)
		double eta = Math.min(10.0, Math.max(1.0, nearest.distanceKm() * 2.0));
		LOGGER.atDebug()
			.addArgument(eta)
			.addArgument(() -> nearest.distanceKm())
			.log("Calculated ETA: {} minutes (distance: {} km)");

		return new BookingResponse("CONFIRMED", nearest.driverId(), nearest.name(), eta);
	}

}
