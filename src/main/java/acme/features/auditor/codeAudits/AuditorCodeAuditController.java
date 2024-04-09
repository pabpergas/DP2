package acme.features.auditor.codeAudits;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.S5.CodeAudit;
import acme.roles.Auditor;

@Controller
public class AuditorCodeAuditController extends AbstractController<Auditor, CodeAudit> {
	
	@Autowired
	private AuditorCodeAuditListMineService listMineService;
	
	@Autowired
	private AuditorCodeAuditShowService showService;
	
	@Autowired
	private AuditorCodeAuditCreateService createService;
	
	@Autowired
	private AuditorCodeAuditUpdateService updateService;
	
	@Autowired 
	private AuditorCodeAuditDeleteService deleteService;
	
	@Autowired
	private AuditorCodeAuditPublishService publishService;
	
	@PostConstruct
	protected void initialice() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
	}

}
