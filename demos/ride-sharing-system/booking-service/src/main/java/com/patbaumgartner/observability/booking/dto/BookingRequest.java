package com.patbaumgartner.observability.booking.dto;

import jakarta.validation.constraints.NotNull;

public record BookingRequest(@NotNull Double pickupLat, @NotNull Double pickupLon, String riderName) {
}
