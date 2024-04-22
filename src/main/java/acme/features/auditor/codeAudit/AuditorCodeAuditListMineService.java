package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S5.CodeAudit;
import acme.entities.S5.Mark;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditListMineService extends AbstractService<Auditor, CodeAudit> {
	
	@Autowired
	private AuditorCodeAuditRepository repo;

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

		Mark mark = object.getMark();

		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "project");
		dataset.put("mark", mark);
		super.getResponse().addData(dataset);
	}
	
}
