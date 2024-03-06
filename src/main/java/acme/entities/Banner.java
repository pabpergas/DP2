
package acme.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import acme.client.data.AbstractEntity;
import acme.validations.entities.BannerValidator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@BannerValidator(initial = "instantationMoment", start = "startDisplay", end = "endDisplay", message = "start moment of display must be after instant moment, between start and end of display must be at least 7 days")
public class Banner extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Past
	private LocalDateTime		instantationMoment;

	@NotNull
	private LocalDateTime		startDisplay;

	@NotNull
	private LocalDateTime		endDisplay;

	@NotNull
	@NotBlank
	private String				pictureLink;

	@NotNull
	@NotBlank
	@Size(min = 1, max = 76)
	private String				slogan;

	@NotNull
	@NotBlank
	private String				documentLink;

}
