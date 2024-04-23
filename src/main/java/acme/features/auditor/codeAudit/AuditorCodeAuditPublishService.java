package acme.features.auditor.codeAudit;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S5.CodeAudit;
import acme.entities.S5.Mark;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditPublishService extends AbstractService<Auditor, CodeAudit> {
	
	@Autowired
	private AuditorCodeAuditRepository repo;
	
	@Override
	public void authorise() {
		boolean status;
		int codeAuditId;
		Auditor auditor;
		CodeAudit codeAudit;

		codeAuditId = super.getRequest().getData("id", int.class);
		codeAudit = this.repo.findById(codeAuditId);
		auditor = codeAudit.getAuditor();

		status = codeAudit != null && codeAudit.getAuditor().equals(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findById(id);

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
		
		Collection<Mark> invalid = Arrays.asList(Mark.F, Mark.FMINUS);
		
		assert !(invalid.contains(object.getMark()));
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;
		
		object.setDraftMode(false);
		this.repo.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Auditor auditor;
		auditor = object.getAuditor();

		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "project");
		dataset.put("auditor", auditor);
		super.getResponse().addData(dataset);
	}
}
