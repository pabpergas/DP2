
package acme.entities.S5;

import java.beans.Transient;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditRecord extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$")
	@Column(unique = true)
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startAudition;

	@PastOrPresent
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endAudition;

	@NotNull
	private Mark				mark;

	@URL
	private String				informationLink;

	@ManyToOne
	private CodeAudit			codeAudit;

	@ManyToOne
	private Auditor				auditor;

	private Boolean				draftMode				= true;
	
	@Transient
	public Long getAuditionPeriod() {
		long diff = this.endAudition.getTime() - this.startAudition.getTime();
		return Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(diff));
	}
}
