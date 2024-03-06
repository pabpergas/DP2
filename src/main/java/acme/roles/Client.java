
package acme.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Client extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "^CLI-[0-9]{4}$")
	@Column(unique = true)
	String						identification;

	@NotBlank
	@Length(max = 75)
	String						companyName;

	@NotBlank
	String						type;

	@Email
	String						email;

	@URL
	@Length(max = 150)
	String						link;

}
