package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S5.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditListMineService extends AbstractService<Auditor, CodeAudit>{

	@Autowired
	private AuditorCodeAuditRepository repo;
	
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	
	@Override
	public void load() {
		Collection<CodeAudit> codeAudit;
		int auditorId;
		
		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		codeAudit = this.repo.findCodeAuditByAuditorId(auditorId);
		super.getBuffer().addData(codeAudit);
	}
	
	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;
		
		Dataset dataset;
		
		dataset = super.unbind(object, 
				"executionDate", "type", "correctiveActions", "mark");
		super.getResponse().addData(dataset);
	}
}
