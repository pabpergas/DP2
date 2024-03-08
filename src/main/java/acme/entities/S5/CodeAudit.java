
package acme.entities.S5;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import acme.client.data.AbstractEntity;
import acme.entities.S1.Project;
import acme.entities.S5.AuditRecord.Mark;
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

	@OneToMany
	private List<AuditRecord>	auditRecords;

	@ManyToOne
	private Auditor				auditor;


	@Transient
	private Mark mark() {
		List<Mark> M = this.getAuditRecords().stream().map(e -> e.getMark()).toList();

		return this.Mode(M);
	}

	private Mark Mode(final List<Mark> M) {
		int maxRepeated = 0;
		Mark mode = null;

		for (int i = 0; i < M.size(); i++) {
			int repeated = 0;
			for (int j = 0; j < M.size(); j++)
				if (M.get(i) == M.get(j))
					repeated++;
			if (repeated > maxRepeated) {
				mode = M.get(i);
				maxRepeated = repeated;
			}
		}

		return mode;

	}


	public enum type {
		STATIC, DINAMIC
	}
}
