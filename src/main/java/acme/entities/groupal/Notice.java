
package acme.entities.groupal;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
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
public class Notice extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				instantiationMoment;

	@NotBlank
	@Length(min = 0, max = 75)
	private String				title;

	@NotBlank
	@Length(min = 0, max = 75)
	private String				author;

	@NotBlank
	@Length(min = 0, max = 100)
	private String				message;

	@Email
	@Length(min = 0, max = 255)
	private String				emailAddress;

	@URL
	@Length(min = 0, max = 255)
	private String				link;


	public void setAuthor(final String username, final String fullName) {
		this.author = username + " - " + fullName;
	}
}
