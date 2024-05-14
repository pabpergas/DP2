
package acme.entities.S5;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Collections;

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
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$", message = "error.code")
	@Column(unique = true)
	private String				code;

	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				executionDate;

	@NotNull
	private CodeAuditType				type;

	@NotBlank
	@Size(max = 100)
	private String				correctiveActions;

	@ManyToOne
	private Auditor				auditor;
	
	private Boolean				draftMode			= true;
	
	@Transient
	public Mark getMark(Collection<AuditRecord> records) {		
		if(!records.isEmpty()) {
			Map<Mark, Integer> repeated = new HashMap<>();
			List<Mark> marks = records.stream().map(e-> e.getMark()).toList();
			
			for( Mark m: marks) {
				if(repeated.containsKey(m)) {
					int i = repeated.get(m) + 1;
					repeated.put(m, i);
				}else {
					repeated.put(m, 1);
				}
			}
			
			return Collections.max(repeated.entrySet(), Map.Entry.comparingByValue()).getKey();
		} else {
			return Mark.FMINUS;
		}
	}
}
