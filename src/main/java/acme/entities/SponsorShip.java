
package acme.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.datatypes.SponsorShipType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SponsorShip extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@ManyToOne
	@Valid
	private Project				project;

	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$")
	@Column(unique = true)
	private String				code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				moment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				duration;

	@Positive
	@NotNull
	private Double				amount;

	@NotNull
	private SponsorShipType		type;

	@Email
	private String				contactEmail;

	@URL
	@Length(max = 255)
	private String				link;
}
