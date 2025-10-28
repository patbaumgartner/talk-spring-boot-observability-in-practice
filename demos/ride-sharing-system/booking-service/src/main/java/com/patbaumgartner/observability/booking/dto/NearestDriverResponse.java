package com.patbaumgartner.observability.booking.dto;

import java.util.UUID;

public record NearestDriverResponse(UUID driverId, String name, double latitude, double longitude, double distanceKm,
		double rating) {
}
