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
public class AuditorCodeAuditDeleteService extends AbstractService<Auditor, CodeAudit> {
	
	@Autowired
	private AuditorCodeAuditRepository repo;
	
	@Autowired
	private AuditorAuditRecordRepository recordRepo;
	
	@Override
	public void authorise() {
		boolean status;
		int codeAuditId;
		Auditor auditor;
		CodeAudit codeAudit;

		codeAuditId = super.getRequest().getData("id", int.class);
		codeAudit = this.repo.findOneById(codeAuditId);
		auditor = codeAudit.getAuditor();

		status = codeAudit != null && codeAudit.getAuditor().equals(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		Auditor auditor;
		Project project;
		
		auditor = object.getAuditor();
		project = repo.findOneProject(super.getRequest().getData("project", int.class));
		
		super.bind(object, "code", "executionDate", "type", "correctiveActions");
		object.setProject(project);
		object.setAuditor(auditor);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findOneById(id);

		super.getBuffer().addData(object);
	}
	
	@Override
	public void validate(final CodeAudit object) {
		assert object != null;
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;
		Collection<AuditRecord> records;
		
		records = recordRepo.findAllByCodeAuditId(object.getId());
		
		this.repo.deleteAll(records);
		this.repo.delete(object);
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
		dataset = super.unbind(object, "code", "executionDate", "correctiveActions", "draftMode");
		dataset.put("mark", mark);
		
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		dataset.put("type", typeChoices.getSelected().getKey());
		dataset.put("types", typeChoices);
		super.getResponse().addData(dataset);
	}

}
