package acme.features.auditor.codeAudit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.S5.CodeAudit;
import acme.roles.Auditor;

@Controller
public class AuditorCodeAuditController extends AbstractController<Auditor, CodeAudit> {
	
	@Autowired
	protected AuditorCodeAuditListMineService		listMineService;

	@Autowired
	protected AuditorCodeAuditShowService			showService;
	
	@Autowired
	protected AuditorCodeAuditCreateService			createService;
	
	@Autowired
	protected AuditorCodeAuditUpdateService			updateService;
	
	@Autowired
	protected AuditorCodeAuditDeleteService			deleteService;
	
	@Autowired
	protected AuditorCodeAuditPublishService		publishService;
	
	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
