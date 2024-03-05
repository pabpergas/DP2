
package acme.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Risk extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "R-[0-9]{3}", message = "The reference must follow the pattern 'R-XXX'")
	@NotBlank
	@Column(unique = true)
	@NotNull
	private String				reference;

	@Temporal(TemporalType.TIMESTAMP)
	@Past(message = "Identification date must be in the past")
	@NotNull
	private Date				date;

	@DecimalMin(value = "0", message = "The impact must be a positive number")
	@NotNull
	private Double				impact;

	@NotNull
	@DecimalMin(value = "0", message = "The probability must be a positive number")
	private Double				probability;

	@NotBlank
	@Size(max = 100, message = "Description must be less than 101 characters")
	@NotNull
	private String				description;

	//Optional
	private String				link;


	@Transient
	private Double Value() {
		return this.impact * this.probability;

	}
}
