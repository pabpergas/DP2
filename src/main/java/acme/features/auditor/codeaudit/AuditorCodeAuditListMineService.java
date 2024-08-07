package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S5.AuditRecord;
import acme.entities.S5.CodeAudit;
import acme.entities.S5.Mark;
import acme.features.auditor.auditrecord.AuditorAuditRecordRepository;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditListMineService extends AbstractService<Auditor, CodeAudit> {
	
	@Autowired
	private AuditorCodeAuditRepository repo;

	@Autowired
	private AuditorAuditRecordRepository recordRepo;
	
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudit> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.repo.findAllByAuditorId(principal.getActiveRoleId());

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;
		
		Collection<AuditRecord> records;
		Mark mark;
		String project;
		
		records = recordRepo.findAllByCodeAuditId(object.getId());

		project = object.getProject().getTitle();
		mark = object.getMark(records);
		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "link");
		dataset.put("mark", mark);
		dataset.put("project", project);
		super.getResponse().addData(dataset);
	}
	
}
