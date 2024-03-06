
package acme.entities.S2;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.S1.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Contract extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;
	@ManyToOne
	@Valid
	private Project				project;

	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$")
	@Column(unique = true)

	private String				code;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)

	private String				providerName;

	@NotBlank
	@Length(max = 75)

	private String				customerName;

	@NotBlank
	@Length(max = 100)

	private String				goals;

	@NotNull
	private Money				budget;

	@URL
	@Length(max = 150)
	private String				link;

	private boolean				draftMode;
}
