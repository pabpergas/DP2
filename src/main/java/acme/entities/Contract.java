
package acme.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Contract extends AbstractEntity {

	@NotBlank
	@NotNull
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}", message = "The code must be in the correct format: [A-Z]{1,3}-[0-9]{3}")
	@Column(unique = true)
	private String			code;

	@NotNull
	@Past
	private LocalDateTime	instantiationMoment;

	@NotBlank
	@NotNull
	@Column(length = 75)
	private String			providerName;

	@NotBlank
	@NotNull
	@Column(length = 75)
	private String			customerName;

	@NotBlank
	@NotNull
	@Column(length = 100)
	private String			goals;

	@PositiveOrZero
	@NotNull
	private double			budget;

	private String			link;

}
