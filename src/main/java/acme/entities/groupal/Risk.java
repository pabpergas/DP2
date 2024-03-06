
package acme.entities.groupal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

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

	@Pattern(regexp = "^R-[0-9]{3}$")
	@NotBlank
	@Column(unique = true)
	private String				reference;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	private Date				identificationDate;

	@Positive
	@NotNull
	private Integer				impact;

	@NotNull
	@Min(0)
	private Double				probability;

	@NotBlank
	@Length(max = 100)
	@NotNull
	private String				description;

	@URL
	@Length(max = 255)
	private String				link;


	@Transient
	private Double Value() {
		return this.impact * this.probability;

	}
}
