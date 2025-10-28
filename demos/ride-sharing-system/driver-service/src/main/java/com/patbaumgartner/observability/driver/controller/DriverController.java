package com.patbaumgartner.observability.driver.controller;

import com.patbaumgartner.observability.driver.dto.NearestDriverRequest;
import com.patbaumgartner.observability.driver.dto.NearestDriverResponse;
import com.patbaumgartner.observability.driver.dto.StatusUpdateRequest;
import com.patbaumgartner.observability.driver.service.DriverService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/drivers")
class DriverController {

	private final DriverService service;

	DriverController(DriverService service) {
		this.service = service;
	}

	@PostMapping("/nearest")
	ResponseEntity<NearestDriverResponse> nearest(@Valid @RequestBody NearestDriverRequest req) {
		LOGGER.atInfo()
			.addArgument(() -> req.latitude())
			.addArgument(() -> req.longitude())
			.log("Searching for nearest driver at location: lat={}, lon={}");
		NearestDriverResponse response = service.findNearest(req.latitude(), req.longitude());
		LOGGER.atInfo()
			.addArgument(() -> response.driverId())
			.addArgument(() -> response.name())
			.addArgument(() -> response.distanceKm())
			.log("Found nearest driver: id={}, name={}, distance={} km");
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{id}/status")
	ResponseEntity<Void> updateStatus(@PathVariable UUID id, @RequestBody @Valid StatusUpdateRequest req) {
		LOGGER.atInfo().addArgument(id).addArgument(() -> req.status()).log("Updating driver {} status to: {}");
		switch (req.status()) {
			case "AVAILABLE" -> {
				service.markAvailable(id);
				LOGGER.info("Driver {} marked as AVAILABLE", id);
			}
			case "BUSY" -> {
				service.markBusy(id);
				LOGGER.info("Driver {} marked as BUSY", id);
			}
			default -> {
				LOGGER.warn("Invalid status update request for driver {}: {}", id, req.status());
				return ResponseEntity.badRequest().build();
			}
		}
		return ResponseEntity.noContent().build();
	}

}
