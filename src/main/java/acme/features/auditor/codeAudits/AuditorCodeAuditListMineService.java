package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
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
		Principal principal;
		
		principal = super.getRequest().getPrincipal();
		codeAudit = this.repo.findCodeAuditByAuditorId(principal.getActiveRoleId());
		
		super.getBuffer().addData(codeAudit);
	}
	
	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;
		
		Dataset dataset;
		
		dataset = super.unbind(object, 
				"code", "executionDate", "type", "correctiveActions", "mark");
		super.getResponse().addData(dataset);
	}
}
