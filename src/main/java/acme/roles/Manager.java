
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Manager extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(min = 0, max = 75)
	String						degree;

	@NotBlank
	@Length(min = 0, max = 100)
	String						overview;

	@NotBlank
	@Length(min = 0, max = 100)
	String						certifications;

	@URL
	@Length(min = 0, max = 255)
	String						link;
}
