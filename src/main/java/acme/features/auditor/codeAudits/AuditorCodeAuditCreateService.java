package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S5.CodeAudit;
import acme.entities.S1.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditCreateService extends AbstractService<Auditor, CodeAudit>{
	
	@Autowired
	private AuditorCodeAuditRepository repo;
	
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	
	@Override
	public void load() {
		CodeAudit object;
		Auditor auditor;
		
		auditor = this.repo.findAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new CodeAudit();
		object.setDraftMode(true);
		object.setAuditor(auditor);
		
		super.getBuffer().addData(object);
	}
	
	@Override
	public void bind(final CodeAudit object) {
		assert object != null;
		
		int projectId;
		Project project;
		
		projectId = super.getRequest().getData("project", int.class);
		project = this.repo.findProjectByCodeAuditId(projectId);
		super.bind(object, "executionDate", "type", "correctiveActions");
		
		object.setProject(project);
	}
	
	@Override
	public void validate(final CodeAudit object) {
		assert object != null;
		
		//TODO: Hacer la validaciones personalizadas
	}
	
	@Override
	public void perform (final CodeAudit object) {
		assert object != null;
		
		this.repo.save(object);
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
				"executionDate", "type", "correctiveActions");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		super.getResponse().addData(dataset);
	}

}
