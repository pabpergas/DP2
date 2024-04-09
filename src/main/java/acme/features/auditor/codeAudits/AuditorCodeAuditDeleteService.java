package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.Project;
import acme.entities.S5.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditDeleteService extends AbstractService<Auditor, CodeAudit> {
	
	@Autowired
	private AuditorCodeAuditRepository repo;
	
	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudit codeAudit;
		Auditor auditor;
		
		masterId = super.getRequest().getData("id", int.class);
		System.out.println("hola1" + masterId);
		
		codeAudit = this.repo.findCodeAuditById(masterId);
		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		System.out.println("hola3" + auditor + codeAudit);
		
		status = codeAudit != null && codeAudit.isDraftMode() && super.getRequest().getPrincipal().hasRole(auditor);
		System.out.println("hola2" + status);
		super.getResponse().setAuthorised(status);
	}
	
	@Override
	public void load() {
		CodeAudit object;
		int id;
		
		id = super.getRequest().getData("id", int.class);
		object = this.repo.findCodeAuditById(id);
		
		super.getBuffer().addData(object);
	}
	
	@Override
	public void bind(final CodeAudit object) {
		assert object != null;
		
		int projectId;
		Project project;
		
		projectId = super.getRequest().getData("project", int.class);
		project = this.repo.findProjectByCodeAuditId(projectId);
		super.bind(object, "code", "executionDate", "type", "correctiveActions", "mark");
		
		object.setProject(project);
	}
	
	@Override
	public void validate(final CodeAudit object) {
		assert object != null;
	}
	
	@Override
	public void perform(final CodeAudit object) {
		assert object != null;
		
		this.repo.delete(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;
		
		int auditorId;
		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;
		
		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		projects = this.repo.findProjectsByAuditorId(auditorId);
		choices = SelectChoices.from(projects, "title", object.getProject());
		
		dataset = super.unbind(object, 
				"code", "executionDate", "type", "correctiveActions", "mark");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		super.getResponse().addData(dataset);
	}

}
