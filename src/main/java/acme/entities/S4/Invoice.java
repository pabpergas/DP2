
package acme.entities.S4;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AbstractEntity {

	@ManyToOne
	@Valid
	private SponsorShip	sponsorShip;

	@NotBlank
	@Pattern(regexp = "^IN-[0-9]{4}-[0-9]{4}$")
	@Column(unique = true)
	private String		code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date		registrationTime;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date		dueDate;

	@NotNull
	private Money		quantity;

	@PositiveOrZero
	@NotNull
	private Double		tax;

	@URL
	@Length(max = 255)
	private String		link;


	@Transient
	private Double totalAmount() {
		Double q = this.quantity.getAmount();

		return q + q * this.tax;
	}

}
