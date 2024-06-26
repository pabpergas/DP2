
package acme.entities.groupal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Risk extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "^R-[0-9]{3}$", message = "{error.risk}")
	@NotBlank
	@Column(unique = true)
	private String				reference;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	private Date				identificationDate;

	@Min(0)
	@Max(100)
	@Digits(integer = 3, fraction = 2)
	private double				impact;

	@Min(0)
	@Max(100)
	@Digits(integer = 3, fraction = 2)
	private double				probability;

	@NotBlank
	@Length(max = 100)
	private String				description;

	@URL
	@Length(min = 0, max = 255)
	private String				link;


	@Transient
	private Double value() {
		return this.impact * this.probability;

	}
}
