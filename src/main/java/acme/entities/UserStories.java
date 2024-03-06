
package acme.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserStories extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(min = 0, max = 75)
	String						title;

	@NotBlank
	@Length(min = 0, max = 100)
	String						description;

	@NotBlank
	@Length(min = 0, max = 100)
	String						acceptanceCriteria;

	@Positive
	Integer						estimatedCost;

	@NotNull
	priorityUserStories			proirity;

	@URL
	@Length(min = 0, max = 100)
	String						link;

	// Relationships ----------------------------------------------------------

	@ManyToOne
	Project						project;

	@ManyToOne(optional = false)
	@NotNull
	Manager						manager;


	public enum priorityUserStories {
		Must, Should, Could, WillNot
	}
}
