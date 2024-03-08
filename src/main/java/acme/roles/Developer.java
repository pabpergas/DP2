
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Developer extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	String						degree;

	@NotBlank
	@Length(max = 100)
	String						specialitation;

	@NotBlank
	@Length(max = 100)
	String						skills;

	@Email
	String						email;

	@URL
	@Length(max = 150)
	String						link;
}
