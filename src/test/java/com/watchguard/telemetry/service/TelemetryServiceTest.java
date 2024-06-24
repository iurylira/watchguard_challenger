package com.watchguard.telemetry.service;

import com.watchguard.telemetry.model.Telemetry;
import com.watchguard.telemetry.repository.TelemetryRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;

@SpringBootTest(classes = TelemetryService.class)
class TelemetryServiceTest {

	@MockBean
	private TelemetryRepository telemetryRepository;

	@Autowired
	private TelemetryService telemetryService;

	@Test
	public void testGetById() {
		final var id = UUID.randomUUID();
		final var telemetry = new Telemetry();

		telemetry.setId(id);

		when(telemetryRepository.findById(id)).thenReturn(Optional.of(telemetry));

		final var result = telemetryService.getById(id);

		assertNotNull(result);
		assertEquals(id, result.getId());

		verify(telemetryRepository, times(1)).findById(id);
	}

	@Test
	public void testGetByIdNotFound() {
		final var id = UUID.randomUUID();
		when(telemetryRepository.findById(id)).thenReturn(Optional.empty());
		assertThrows(EntityNotFoundException.class, () -> telemetryService.getById(id));
	}

	@Test
	public void testSave() {
		final var telemetry = new Telemetry();

		when(telemetryRepository.save(any(Telemetry.class))).thenReturn(telemetry);

		assertNotNull(telemetryService.save(telemetry));

		verify(telemetryRepository, times(1)).save(any(Telemetry.class));
	}

	@Test
	public void testUpdate() {
		final var id = UUID.randomUUID();
		final var telemetry = new Telemetry();

		telemetry.setId(id);

		when(telemetryRepository.existsById(id)).thenReturn(true);
		when(telemetryRepository.save(any(Telemetry.class))).thenReturn(telemetry);

		final Telemetry result = telemetryService.update(telemetry);

		assertNotNull(result);
		assertEquals(id, result.getId());

		verify(telemetryRepository, times(1)).existsById(id);
		verify(telemetryRepository, times(1)).save(any(Telemetry.class));
	}

	@Test
	public void testUpdateNotFound() {
		final var id = UUID.randomUUID();
		final var telemetry = new Telemetry();

		telemetry.setId(id);

		when(telemetryRepository.existsById(id)).thenReturn(false);
		assertThrows(EntityNotFoundException.class, () -> telemetryService.update(telemetry));
	}

	@Test
	public void testGetTelemetriesByCriteria() {
		final var tenant = UUID.randomUUID();
		final var source = UUID.randomUUID();
		final var metric = "temperature";

		final var telemetryList = new ArrayList<Telemetry>();

		when(telemetryRepository.findAll(any(Specification.class))).thenReturn(telemetryList);

		final var result = telemetryService.getTelemetriesByCriteria(tenant, source, metric);

		assertNotNull(result);
		assertEquals(telemetryList, result);

		verify(telemetryRepository, times(1)).findAll(any(Specification.class));
	}

	@Test
	public void testGetTemperaturesByTenantAndSource() {
		final var tenant = UUID.randomUUID();
		final var source = UUID.randomUUID();

		final var temperatures = List.of(BigDecimal.valueOf(36.5), BigDecimal.valueOf(40.0));

		when(telemetryRepository.getTemperaturesByTenantAndSource(tenant, source)).thenReturn(temperatures);

		final var result = telemetryService.getTemperaturesByTenantAndSource(tenant, source);

		assertNotNull(result);
		assertEquals(temperatures.size(), result.size());

		verify(telemetryRepository, times(1)).getTemperaturesByTenantAndSource(tenant, source);
	}

	@Test
	public void testDelete() {
		final var id = UUID.randomUUID();

		when(telemetryRepository.existsById(id)).thenReturn(true);

		telemetryService.delete(id);

		verify(telemetryRepository, times(1)).deleteById(id);
	}

	@Test
	public void testDeleteTelemetryNotFound() {
		final var id = UUID.randomUUID();

		when(telemetryRepository.existsById(id)).thenReturn(false);

		assertThrows(EntityNotFoundException.class, () -> telemetryService.delete(id));

		verify(telemetryRepository, never()).deleteById(id);
	}

}
