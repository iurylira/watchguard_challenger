package com.watchguard.telemetry.repository;

import com.watchguard.telemetry.model.Telemetry;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetryRepository extends JpaRepository<Telemetry, UUID> {

	List<Telemetry> findAll(Specification<Telemetry> spec);

	@Query("SELECT t.value FROM Telemetry t WHERE t.tenant = :tenant AND t.source = :source")
	List<BigDecimal> getTemperaturesByTenantAndSource(UUID tenant, UUID source);

}
