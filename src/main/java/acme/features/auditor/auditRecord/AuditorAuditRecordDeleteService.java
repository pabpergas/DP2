package acme.features.auditor.auditRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S5.AuditRecord;
import acme.entities.S5.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordDeleteService extends AbstractService<Auditor, AuditRecord> {
	
	@Autowired
	private AuditorAuditRecordRepository repo;
	
	@Override
	public void authorise() {
		boolean status;
		int auditRecordId;
		Auditor auditor;
		AuditRecord auditRecord;

		auditRecordId = super.getRequest().getData("id", int.class);
		auditRecord = this.repo.findById(auditRecordId);
		auditor = auditRecord.getCodeAudit().getAuditor();

		status = auditRecord != null && auditRecord.getCodeAudit().getAuditor().equals(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		CodeAudit codeAudit;
		codeAudit = object.getCodeAudit();

		super.bind(object, "code", "startAudition", "endAudition", "mark", "informationLink");
		object.setCodeAudit(codeAudit);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findById(id);

		super.getBuffer().addData(object);
	}
	
	@Override
	public void validate(final AuditRecord object) {
		assert object.getDraftMode();
		
		assert object != null;
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;
		
		this.repo.delete(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		CodeAudit codeAudit;
		codeAudit = object.getCodeAudit();

		Dataset dataset;
		dataset = super.unbind(object, "code", "startAudition", "endAudition", "mark", "informationLink");
		dataset.put("codeAudit", codeAudit);
		super.getResponse().addData(dataset);
	}

}
