
package acme.entities.S3;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.validations.ValidTrainingSession;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidTrainingSession
public class Sessions extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "^TS-[A-Z]{1,3}-[0-9]{3}$", message = "error.session")
	@Column(unique = true)
	private String				code;

	@ManyToOne
	@Valid
	private Training			training;

	@NotBlank
	@Length(max = 76)
	private String				location;

	@NotBlank
	@Length(max = 76)
	private String				instructor;

	@NotNull
	@Email
	private String				email;

	@URL
	private String				link;

	@NotNull
	private Date				startDate;

	@NotNull
	private Date				endDate;

	@NotNull
	private int					durationInWeeks;

	private boolean				draftMode;

}
