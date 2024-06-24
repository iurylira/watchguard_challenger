package com.watchguard.telemetry.service;

import com.watchguard.telemetry.model.Telemetry;
import com.watchguard.telemetry.repository.TelemetryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class TelemetryService {
	private final TelemetryRepository telemetryRepository;

	public Telemetry getById(final UUID id) {
		return telemetryRepository.findById(id)
				.orElseThrow(() -> {
					log.error("Telemetry not found with id: {}", id);
					return new EntityNotFoundException("Telemetry not found with id: " + id);
				});
	}

	public Telemetry save(final Telemetry telemetry) {
		return telemetryRepository.save(telemetry);
	}

	public Telemetry update(final Telemetry telemetry) {
		if (telemetry.getId() == null) {
			final var msg = "Telemetry id not found";
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}

		if (!telemetryRepository.existsById(telemetry.getId())) {
			log.error("Telemetry not found with id: {}", telemetry.getId());
			throw new EntityNotFoundException("Telemetry not found with id: " + telemetry.getId());
		}

		return telemetryRepository.save(telemetry);
	}

	public List<Telemetry> getTelemetriesByCriteria(final UUID tenant, final UUID source, final String metric) {
		return telemetryRepository.findAll(getSpecification(tenant, source, metric));
	}

	public List<BigDecimal> getTemperaturesByTenantAndSource(final UUID tenant, final UUID source) {
		return telemetryRepository.getTemperaturesByTenantAndSource(tenant, source);
	}

	public void delete(final UUID id) {
		if (telemetryRepository.existsById(id)) {
			telemetryRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("Telemetry not found with id: " + id);
		}
	}

	private Specification<Telemetry> getSpecification(final UUID tenant, final UUID source, final String metric) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (tenant != null) {
				predicates.add(criteriaBuilder.equal(root.get("tenant"), tenant));
			}
			if (source != null) {
				predicates.add(criteriaBuilder.equal(root.get("source"), source));
			}
			if (metric != null) {
				predicates.add(criteriaBuilder.equal(root.get("metric"), metric));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
