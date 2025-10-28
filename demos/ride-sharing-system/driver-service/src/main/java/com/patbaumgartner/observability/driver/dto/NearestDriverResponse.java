package com.patbaumgartner.observability.driver.dto;

import java.util.UUID;

public record NearestDriverResponse(UUID driverId, String name, double latitude, double longitude, double distanceKm,
		double rating) {
}
