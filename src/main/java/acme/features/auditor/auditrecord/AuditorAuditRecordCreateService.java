package acme.features.auditor.auditrecord;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.views.SelectChoices;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.features.auditor.codeaudit.AuditorCodeAuditRepository;
import acme.entities.S5.AuditRecord;
import acme.entities.S5.CodeAudit;
import acme.entities.S5.Mark;
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
		Auditor auditor;

		auditor = this.codeAuditRepo.findAuditorByAuditorId(super.getRequest().getPrincipal().getActiveRoleId());
		codeAuditId = super.getRequest().getData("codeAuditId", int.class);
		codeAudit = this.codeAuditRepo.findOneById(codeAuditId);
		
		object = new AuditRecord();
		object.setCodeAudit(codeAudit);
		object.setAuditor(auditor);
		
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		CodeAudit codeAudit;
		Auditor auditor;
		
		codeAudit = object.getCodeAudit();
		auditor = object.getAuditor();
		super.bind(object, "code", "startAudition", "endAudition", "mark", "informationLink", "draftMode");
		
		object.setCodeAudit(codeAudit);
		object.setAuditor(auditor);
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
		
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord existing;
			existing = this.repo.findOneByCode(object.getCode());
			
			super.state(existing == null, "code", "auditor.auditRecord.error.code.existing");
		}
	}
	
	@Override
	public void perform(final AuditRecord object) {
		assert object != null;
		
		object.setDraftMode(true);
		this.repo.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset;
		CodeAudit codeAudit;
		SelectChoices marks;
		
		marks = SelectChoices.from(Mark.class, object.getMark());
		codeAudit = object.getCodeAudit();
		dataset = super.unbind(object, "code", "startAudition", "endAudition", "informationLink", "draftMode");
		
		dataset.put("mark", marks.getSelected().getKey());
		dataset.put("marks", marks);
		dataset.put("codeAudit", codeAudit);
		
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
