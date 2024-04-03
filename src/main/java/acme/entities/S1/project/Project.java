
package acme.entities.S1.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Pattern(regexp = "^[A-Z]{3}-[0-9]{4}$")
	@NotBlank
	@Column(unique = true)
	String						code;

	@NotBlank
	@Length(min = 0, max = 75)
	String						title;

	@NotBlank
	@Length(min = 0, max = 100)
	String						summary;

	@NotNull
	Boolean						hasFatalErrors;

	@PositiveOrZero
	Integer						cost;

	@URL
	@Length(min = 0, max = 100)
	String						link;

	@NotNull
	Boolean						draftMode;

	// Relationships ----------------------------------------------------------

	@ManyToOne(optional = false)
	@NotNull
	Manager						manager;

}
