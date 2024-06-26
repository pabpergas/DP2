package acme.features.auditor.auditrecord;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S5.AuditRecord;
import acme.entities.S5.CodeAudit;
import acme.entities.S5.Mark;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordUpdateService extends AbstractService<Auditor, AuditRecord> {
	
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
		assert object != null;
		
		if (!super.getBuffer().getErrors().hasErrors("endAudition")) {
			long diffInMili;
			long diffInHour;

			if (object.getStartAudition() != null) {
				diffInMili = object.getEndAudition().getTime() - object.getStartAudition().getTime();
				diffInHour = TimeUnit.MILLISECONDS.toHours(diffInMili);
				super.state(diffInHour >= 1, "endAudition", "auditor.auditRecord.error.duration");
			}
		}
		
		if (!super.getBuffer().getErrors().hasErrors("startAudition")) {
			if(object.getEndAudition() != null) {
				super.state(object.getStartAudition().before(object.getEndAudition()),
						"startAudition", "auditor.auditRecord.error.badDates");
			}
			}
		
		if(!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord existing;
			existing = this.repo.findOneByCode(object.getCode());
			if(existing != null) {
			if(!existing.equals(object)) {
				
				super.state(existing == null, "code", "auditor.codeAudit.error.code.duplicated");}
		}
		}
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		this.repo.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;
		SelectChoices marks;
		CodeAudit codeAudit;
		
		codeAudit = object.getCodeAudit();
		marks = SelectChoices.from(Mark.class, object.getMark());
		dataset = super.unbind(object, "code", "startAudition", "endAudition", "informationLink", "draftMode");

		dataset.put("codeAudit", codeAudit);
		dataset.put("mark", marks.getSelected().getKey());
		dataset.put("marks", marks);
		
		super.getResponse().addData(dataset);
	}

}
