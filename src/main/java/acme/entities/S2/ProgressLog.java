
package acme.entities.S2;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProgressLog extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@NotNull
	@Pattern(regexp = "PG-[A-Z]{1,2}-[0-9]{4}", message = "The record id must be in the correct format: PG-[A-Z]{1,2}-[0-9]{4}")
	@Column(unique = true)
	private String				recordId;

	@Positive
	@NotNull
	private double				completenessPercentage;

	@NotBlank
	@NotNull
	@Column(length = 100)
	private String				progressComment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registrationMoment;

	@NotBlank
	@NotNull
	@Column(length = 75)
	private String				responsiblePerson;

}
