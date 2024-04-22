package acme.features.auditor.auditRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.features.auditor.codeAudit.AuditorCodeAuditRepository;
import acme.entities.S5.AuditRecord;
import acme.entities.S5.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordCreateService extends AbstractService<Auditor, AuditRecord> {
	
	@Autowired
	private AuditorAuditRecordRepository repo;
	
	@Autowired
	private AuditorCodeAuditRepository codeAuditRepo;
	
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AuditRecord object;
		CodeAudit codeAudit;
		int codeAuditId;

		System.out.println("funcion1");
		codeAuditId = super.getRequest().getData("codeAuditId", int.class);
		codeAudit = this.codeAuditRepo.findById(codeAuditId);

		System.out.println("funciona2");
		object = new AuditRecord();
		object.setCodeAudit(codeAudit);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		CodeAudit codeAudit;
		codeAudit = object.getCodeAudit();
		super.bind(object, "code", "startAudition", "endAudition", "mark", "informationLink");
		object.setCodeAudit(codeAudit);
		System.out.println(object);
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;
	}
	
	@Override
	public void perform(final AuditRecord object) {
		assert object != null;
		this.repo.save(object);
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
