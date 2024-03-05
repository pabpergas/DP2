
package acme.entities;

import java.sql.Date;
import java.time.Duration;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import acme.client.data.AbstractEntity;
import acme.datatypes.SponsorShipType;

public class SponsorShip extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Falta relacion con project

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}", message = "The code must be in the correct format: ABC-123")
	@Column(unique = true)
	private String				code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				moment;

	//En el servicio validar que la duration 
	@NotNull
	private Duration			duration;

	@Positive
	@NotNull
	private double				amount;

	@NotBlank
	@NotNull
	private SponsorShipType		type;

	@Email
	private String				contactEmail;

	private String				link;
}