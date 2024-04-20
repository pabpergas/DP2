
package acme.entities.S4;

import java.util.Date;

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

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.S1.Project;
import acme.roles.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SponsorShip extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$", message = "{sponsor.sponsorShip.error.code}")
	//@Column(unique = true)
	private String				code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				moment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endDate;

	@NotNull
	@Valid
	private Money				amount;

	@NotNull
	private SponsorShipType		type;

	@Email
	@Length(min = 0, max = 255)
	private String				contactEmail;

	@URL
	@Length(min = 0, max = 255)
	private String				link;

	private boolean				draftMode			= true;

	@ManyToOne
	@Valid
	@NotNull
	private Sponsor				sponsor;

	@ManyToOne
	@Valid
	@NotNull
	private Project				project;


	public enum SponsorShipType {
		FINANCIAL, IN_KIND
	}

}
