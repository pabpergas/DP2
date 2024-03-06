
package acme.entities.S1;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.Priority;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserStories extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

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
	Double						estimatedCost;

	Priority					proirity;

	@URL
	String						link;
}
