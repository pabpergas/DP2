
package acme.entities.groupal;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				instantationMoment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startDisplay;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endDisplay;

	@NotNull
	@URL
	@Length(min = 0, max = 255)
	private String				pictureLink;

	@NotBlank
	@Length(min = 1, max = 75)
	private String				slogan;

	@NotNull
	@URL
	@Length(min = 0, max = 255)
	private String				documentLink;

}
