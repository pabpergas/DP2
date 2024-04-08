
package acme.entities.S1.project;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import acme.client.data.AbstractEntity;
import acme.entities.S1.userStrories.UserStories;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProjectUserStories extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Relationships ----------------------------------------------------------

	@NotNull
	@ManyToOne(optional = false)
	@Valid
	@OnDelete(action = OnDeleteAction.CASCADE)
	protected Project			project;

	@NotNull
	@ManyToOne(optional = false)
	@Valid
	protected UserStories		userStories;

}
