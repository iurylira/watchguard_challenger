package com.watchguard.telemetry.controller;

import com.watchguard.telemetry.model.Telemetry;
import com.watchguard.telemetry.service.TelemetryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/telemetries")
public class TelemetryController {
	private final TelemetryService telemetryService;

	@GetMapping("/{id}")
	public ResponseEntity<Telemetry> getById(@PathVariable("id") @NotNull final UUID id) {
		return ResponseEntity.ok(telemetryService.getById(id));
	}

	@GetMapping
	public ResponseEntity<List<Telemetry>> getTelemetries(@RequestParam(value = "tenant", required = false) final UUID tenant,
														  @RequestParam(value = "source", required = false) final UUID source,
														  @RequestParam(value = "metric", required = false) final String metric) {
		final var telemetries = telemetryService.getTelemetriesByCriteria(tenant, source, metric);

		if (telemetries.isEmpty())
			return ResponseEntity.noContent().build();

		return ResponseEntity.ok(telemetries);
	}

	@GetMapping("/temperatures")
	public ResponseEntity<List<BigDecimal>> getTemperatures(@RequestParam(value = "tenant") final UUID tenant,
															@RequestParam(value = "source") final UUID source) {
		final var temperatures = telemetryService.getTemperaturesByTenantAndSource(tenant, source);

		if (temperatures.isEmpty())
			return ResponseEntity.noContent().build();

		return ResponseEntity.ok(temperatures);
	}

	@PostMapping
	public ResponseEntity<Telemetry> save(@Valid @RequestBody final Telemetry telemetry) {
		return ResponseEntity.status(HttpStatus.CREATED).body(telemetryService.save(telemetry));
	}

	@PutMapping
	public ResponseEntity<Telemetry> update(@Valid @RequestBody final Telemetry telemetry) {
		return ResponseEntity.status(HttpStatus.OK).body(telemetryService.update(telemetry));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") @NotNull final UUID id) {
		telemetryService.delete(id);
		return ResponseEntity.ok().build();
	}
}
