
package acme.entities.groupal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
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
public class Claim extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "^C-[0-9]{4}$")
	@NotBlank
	@Column(unique = true)
	private String				code;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				instantiation;

	@NotBlank
	@Length(min = 0, max = 75)
	private String				heading;

	@NotBlank
	@Length(min = 0, max = 100)
	private String				description;

	@NotBlank
	@Length(min = 0, max = 100)
	private String				department;

	@Email
	@Length(min = 0, max = 255)
	private String				email;

	@URL
	@Length(min = 0, max = 255)
	private String				link;

}
