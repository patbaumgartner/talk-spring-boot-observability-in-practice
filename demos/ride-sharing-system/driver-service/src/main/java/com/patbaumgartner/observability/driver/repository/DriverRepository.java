package com.patbaumgartner.observability.driver.repository;

import com.patbaumgartner.observability.driver.domain.Driver;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Observed
@Repository
public class DriverRepository {

	private final JdbcClient jdbc;

	public DriverRepository(JdbcClient jdbc) {
		this.jdbc = jdbc;
	}

	public Optional<Driver> findById(UUID id) {
		LOGGER.debug("Finding driver by id: {}", id);
		return jdbc.sql("select * from drivers where id = :id").param("id", id).query(Driver.class).optional();
	}

	public int updateStatus(UUID id, String status) {
		LOGGER.debug("Updating driver {} status to: {}", id, status);
		int result = jdbc.sql("update drivers set status = :s where id = :id")
			.param("s", status)
			.param("id", id)
			.update();
		LOGGER.debug("Update affected {} row(s)", result);
		return result;
	}

	public List<Driver> findAvailable() {
		LOGGER.debug("Finding all available drivers");
		List<Driver> drivers = jdbc.sql("select * from drivers where status = 'AVAILABLE'").query(Driver.class).list();
		LOGGER.atDebug().addArgument(() -> drivers.size()).log("Found {} available driver(s)");
		return drivers;
	}

	// Haversine distance computed in SQL for efficiency
	public Optional<DriverDistance> findNearestAvailable(double lat, double lon) {
		LOGGER.debug("Finding nearest available driver to location: lat={}, lon={}", lat, lon);
		String sql = """
				select id, name, latitude, longitude, status, rating,
				  2 * 6371 * asin(
				    sqrt(
				      pow(sin(radians((:lat - latitude)/2)),2) +
				      cos(radians(latitude)) * cos(radians(:lat)) *
				      pow(sin(radians((:lon - longitude)/2)),2)
				    )
				  ) as distance_km
				from drivers
				where status = 'AVAILABLE'
				order by distance_km asc, rating desc
				limit 1
				""";
		Optional<DriverDistance> result = jdbc.sql(sql)
			.param("lat", lat)
			.param("lon", lon)
			.query((rs, i) -> new DriverDistance(UUID.fromString(rs.getString("id")), rs.getString("name"),
					rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getDouble("distance_km"),
					rs.getDouble("rating")))
			.optional();

		if (result.isPresent()) {
			LOGGER.atDebug()
				.addArgument(() -> result.get().id())
				.addArgument(() -> result.get().distanceKm())
				.log("Nearest driver found: id={}, distance={} km");
		}
		else {
			LOGGER.warn("No available drivers found near location: lat={}, lon={}", lat, lon);
		}

		return result;
	}

	public record DriverDistance(UUID id, String name, double lat, double lon, double distanceKm, double rating) {
	}

}
