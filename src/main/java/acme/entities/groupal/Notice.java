
package acme.entities.groupal;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

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
	@Size(max = 75)
	private String				title;

	@NotBlank
	@Size(max = 75)
	private String				author;

	@NotBlank
	@Size(max = 100)
	private String				message;

	@Email
	private String				emailAddress;

	@URL
	private String				link;


	public void setAuthor(final String username, final String fullName) {
		this.author = username + " - " + fullName;
	}
}
