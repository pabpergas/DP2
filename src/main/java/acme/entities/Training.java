
package acme.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.datatypes.Difficulty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Training extends AbstractEntity {

	@NotBlank
	@NotNull
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}", message = "The code must be in the correct format: [A-Z]{1,3}-[0-9]{3}")
	@Column(unique = true)
	private String		code;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date		moment;

	@NotBlank
	@NotNull
	@Length(max = 100)
	private String		details;

	@NotBlank
	@NotNull
	@Enumerated(EnumType.STRING)
	private Difficulty	difficulty;

	@URL
	private String		link;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date		updateMoment;

	@NotNull
	@Column(name = "estimated_total_time")
	private int			estimatedTotalTime; // Represented in minutes

	// Constructors, getters, and setters can be added here
}
