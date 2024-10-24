
package acme.entities.S1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
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
@Table(indexes = {
	@Index(columnList = "manager_id"), @Index(columnList = "id")
})
public class Project extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Pattern(regexp = "^[A-Z]{3}-[0-9]{4}$", message = "{error.project.code}")
	@NotBlank
	@Column(unique = true)
	String						code;

	@NotBlank
	@Length(min = 0, max = 75)
	String						title;

	@NotBlank
	@Length(min = 0, max = 100)
	String						summary;

	boolean						hasFatalErrors;

	@PositiveOrZero
	@Max(10000)
	int							cost;

	@URL
	@Length(min = 0, max = 255)
	String						link;

	boolean						draftMode;

	// Relationships ----------------------------------------------------------

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	Manager						manager;

}
