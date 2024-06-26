package acme.features.auditor.auditrecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S5.AuditRecord;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordListService extends AbstractService<Auditor, AuditRecord> {
	
	@Autowired
	private AuditorAuditRecordRepository repo;
	
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<AuditRecord> object;
		int codeAuditId;

		codeAuditId = super.getRequest().getData("codeAuditId", int.class);
		object = this.repo.findAllByCodeAuditId(codeAuditId);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;
		
		dataset = super.unbind(object, "code", "startAudition", "endAudition", "mark", "informationLink", "draftMode");
		
		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<AuditRecord> objects) {
		assert objects != null;

		int codeAuditId;

		codeAuditId = super.getRequest().getData("codeAuditId", int.class);

		super.getResponse().addGlobal("codeAuditId", codeAuditId);
	}
}
