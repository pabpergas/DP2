
package acme.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Objective extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Size(max = 76, message = "Title must be shorter than 76 characters")
	@Column(nullable = false)
	private String				title;

	@NotBlank
	@Size(max = 100, message = "Description must be shorter than 101 characters")
	@Column(nullable = false)
	private String				description;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Priority			priority;

	@NotNull
	private boolean				critical;

	@NotNull
	@Past(message = "Instantiation moment must be in the past")
	private Instant				instantiationMoment;

	@NotNull
	private Instant				duration;

	private String				link;


	public enum Priority {
		LOW, MEDIUM, HIGH
	}
}
