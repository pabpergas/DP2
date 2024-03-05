
package acme.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AbstractEntity {

	@NotBlank
	@NotNull
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}", message = "The code must be in the correct format: IN-XXXX-XXXX")
	@Column(unique = true)
	private String	code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date	registrationTime;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date	dueDate;

	@Positive
	@NotNull
	private Integer	quantity;

	@PositiveOrZero
	@NotNull
	private Double	tax;


	private Double calculateTotalAmount() {
		return this.quantity + this.quantity * this.tax;
	}


	private String link;

}
