package com.patbaumgartner.observability.driver.domain;

import java.util.UUID;

public record Driver(UUID id, String name, double latitude, double longitude, String status, double rating) {
}
