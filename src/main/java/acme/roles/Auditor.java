
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Auditor extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Size(max = 75)
	String						firm;

	@NotBlank
	@Size(max = 25)
	String						professionaId;

	@NotBlank
	@Size(max = 100)
	String						certifications;

	@URL
	String						informationalLink;

}
