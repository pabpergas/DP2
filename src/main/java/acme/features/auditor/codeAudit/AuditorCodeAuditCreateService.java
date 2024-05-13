package acme.features.auditor.codeAudit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S5.CodeAudit;
import acme.entities.S5.Mark;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditCreateService extends AbstractService<Auditor, CodeAudit> {
	
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

		auditor = this.repo.findAuditorByAuditorId(super.getRequest().getPrincipal().getActiveRoleId());
		object = new CodeAudit();
		object.setAuditor(auditor);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		Auditor auditor;
		auditor = object.getAuditor();

		super.bind(object, "code", "executionDate", "type", "correctiveActions", "project");
		object.setAuditor(auditor);
	}
	
	@Override
	public void validate(final CodeAudit object) {
		assert object != null;
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;
		
		this.repo.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Auditor auditor;
		auditor = object.getAuditor();
		Mark mark = object.getMark();
		

		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "project");
		dataset.put("auditor", auditor);
		dataset.put("mark", mark);
		super.getResponse().addData(dataset);
	}

}
