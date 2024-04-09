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
	protected void initialise() {
		super.addBasicCommand("list", listMineService);
		super.addBasicCommand("show", showService);
		super.addBasicCommand("create", createService);
		super.addBasicCommand("update", updateService);
		super.addBasicCommand("delete", deleteService);
		super.addBasicCommand("publish", publishService);
	}

}
