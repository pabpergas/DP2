
package acme.entities.S3;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public class Training extends AbstractEntity {

	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$")
	@Column(unique = true)
	private String		code;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date		moment;

	@NotBlank
	@Length(max = 100)
	private String		details;

	@NotNull
	private Difficulty	difficulty;

	@URL
	private String		link;

	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date		updateMoment;

	private int			estimatedTotalTime;


	public enum Difficulty {
		BASIC, INTERMEDIATE, ADVANCED
	}

}
