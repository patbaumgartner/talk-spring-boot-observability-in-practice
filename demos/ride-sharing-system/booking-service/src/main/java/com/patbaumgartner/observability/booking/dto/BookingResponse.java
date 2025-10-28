package com.patbaumgartner.observability.booking.dto;

import java.util.UUID;

public record BookingResponse(String status, UUID driverId, String driverName, double etaMinutes) {
}
