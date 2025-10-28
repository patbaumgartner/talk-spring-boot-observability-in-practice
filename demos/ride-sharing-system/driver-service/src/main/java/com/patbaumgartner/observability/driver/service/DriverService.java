package com.patbaumgartner.observability.driver.service;

import com.patbaumgartner.observability.driver.dto.NearestDriverResponse;
import com.patbaumgartner.observability.driver.repository.DriverRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class DriverService {

	private final DriverRepository repo;

	public DriverService(DriverRepository repo) {
		this.repo = repo;
	}

	public NearestDriverResponse findNearest(double lat, double lon) {
		LOGGER.debug("Searching for nearest available driver at location: lat={}, lon={}", lat, lon);
		var dd = repo.findNearestAvailable(lat, lon).orElseThrow(() -> {
			LOGGER.error("No available drivers found at location: lat={}, lon={}", lat, lon);
			return new IllegalStateException("No available drivers");
		});
		LOGGER.atInfo()
			.addArgument(() -> dd.id())
			.addArgument(() -> dd.name())
			.addArgument(() -> dd.distanceKm())
			.addArgument(() -> dd.rating())
			.log("Nearest driver found: id={}, name={}, distance={} km, rating={}");
		return new NearestDriverResponse(dd.id(), dd.name(), dd.lat(), dd.lon(), dd.distanceKm(), dd.rating());
	}

	public void markBusy(UUID id) {
		LOGGER.debug("Attempting to mark driver {} as BUSY", id);
		int updated = repo.updateStatus(id, "BUSY");
		if (updated == 0) {
			LOGGER.error("Failed to mark driver {} as BUSY - driver not found", id);
			throw new IllegalArgumentException("Driver not found");
		}
		LOGGER.debug("Successfully marked driver {} as BUSY", id);
	}

	public void markAvailable(UUID id) {
		LOGGER.debug("Attempting to mark driver {} as AVAILABLE", id);
		int updated = repo.updateStatus(id, "AVAILABLE");
		if (updated == 0) {
			LOGGER.error("Failed to mark driver {} as AVAILABLE - driver not found", id);
			throw new IllegalArgumentException("Driver not found");
		}
		LOGGER.debug("Successfully marked driver {} as AVAILABLE", id);
	}

}
