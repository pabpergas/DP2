package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.Project;
import acme.entities.S5.AuditRecord;
import acme.entities.S5.CodeAudit;
import acme.entities.S5.CodeAuditType;
import acme.entities.S5.Mark;
import acme.features.auditor.auditrecord.AuditorAuditRecordRepository;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditCreateService extends AbstractService<Auditor, CodeAudit> {
	
	@Autowired
	private AuditorCodeAuditRepository repo;
	
	@Autowired
	private AuditorAuditRecordRepository recordRepo;
	
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CodeAudit object;
		Auditor auditor;

		auditor = this.repo.findAuditorByAuditorId(super.getRequest().getPrincipal().getActiveRoleId());
		object = new CodeAudit();
		object.setAuditor(auditor);
		object.setDraftMode(true);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		Auditor auditor;
		int projectId;
		Project project;
		
		auditor = object.getAuditor();
		projectId = super.getRequest().getData("project", int.class);
		project = this.repo.findOneProject(projectId);
		
		super.bind(object, "code", "executionDate", "type", "correctiveActions", "link");
		object.setProject(project);
		object.setAuditor(auditor);
	}
	
	@Override
	public void validate(final CodeAudit object) {
		assert object != null;
		
		if(!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit existing;
			existing = this.repo.findOneBycode(object.getCode());
			
			super.state(existing == null, "code", "auditor.codeAudit.error.code.duplicated");
		}
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;
		
		this.repo.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<AuditRecord> records;
		Collection<Project> projects;
		SelectChoices choices;
		SelectChoices typeChoices;
		Mark mark;
		
		records = recordRepo.findAllByCodeAuditId(object.getId());
		projects = this.repo.findAllProjects();
		
		mark = object.getMark(records);
		choices = SelectChoices.from(projects, "title", object.getProject());
		typeChoices = SelectChoices.from(CodeAuditType.class, object.getType());

		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "correctiveActions", "draftMode", "link");
		dataset.put("mark", mark);
		
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		dataset.put("type", typeChoices.getSelected().getKey());
		dataset.put("types", typeChoices);
		super.getResponse().addData(dataset);
	}

}
