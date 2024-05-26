
package acme.entities.S4;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

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

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "^IN-[0-9]{4}-[0-9]{4}$", message = "{sponsor.invoice.error.code}")
	@Column(unique = true)
	private String				code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				registrationTime;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				dueDate;

	//Si la tasa fuera del 100% y quantity = USD 500.000, totalAmount = 1.000.000, limite superior => 500.000
	@NotNull
	@Valid
	private Money				quantity;

	@Min(0)
	@Max(100)
	@NotNull
	@Digits(integer = 3, fraction = 2)
	private Double				tax;

	@URL
	@Length(min = 0, max = 255)
	private String				link;


	@Transient
	public Double totalAmount() {
		Double q = this.quantity.getAmount();

		return q + q * this.tax / 100;
	}


	private boolean		draftMode	= true;

	@ManyToOne
	@Valid
	@NotNull
	private SponsorShip	sponsorShip;

}
