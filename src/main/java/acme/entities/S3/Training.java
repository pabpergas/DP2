
package acme.entities.S3;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.S1.Project;
import acme.roles.Developer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Training extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$", message = "code")
	@Column(unique = true)
	private String				code;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				moment;

	@NotBlank
	@Length(max = 100)
	private String				details;

	@NotNull
	private Difficulty			difficulty;

	@URL
	private String				link;

	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				updateMoment;

	private int					estimatedTotalTime;
	private boolean				draftMode;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "developer_id")
	private Developer			developer;

	@NotNull
	@ManyToOne(optional = false)
	private Project				project;


	public enum Difficulty {
		BASIC, INTERMEDIATE, ADVANCED
	}

}
