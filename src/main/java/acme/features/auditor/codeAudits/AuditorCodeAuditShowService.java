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
public class AuditorCodeAuditShowService extends AbstractService<Auditor, CodeAudit> {
	
	@Autowired
	private AuditorCodeAuditRepository repo;
	
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
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
