
package acme.entities.S5;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import acme.client.data.AbstractEntity;
import acme.entities.S1.Project;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CodeAudit extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@ManyToOne
	@Valid
	private Project				project;

	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$")
	@Column(unique = true)
	private String				code;

	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				executionDate;

	@NotNull
	private type				type;

	@NotBlank
	@Size(max = 100)
	private String				correctiveActions;


	@ManyToOne
	private Auditor				auditor;

	private boolean draftMode	= true;

	public enum type {
		STATIC, DINAMIC
	}
}
