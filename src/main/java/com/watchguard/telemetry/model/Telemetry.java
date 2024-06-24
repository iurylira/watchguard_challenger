package com.watchguard.telemetry.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Telemetry {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@NotNull
	private UUID tenant;

	@NotNull
	private UUID source;

	@NotNull
	private OffsetDateTime timestamp;

	@NotNull
	@Column(name = "'metric'")
	private String metric; //In the future if we know all values we could promote to Enum

	@NotNull
	@Column(name = "'value'")
	private BigDecimal value;

	@Version
	private Long version;
}
