package acme.features.auditor.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.roles.Auditor;
import acme.forms.AuditorDashboard;

@Controller
public class AuditorDashboardController extends AbstractController<Auditor, AuditorDashboard>{
	
	@Autowired
	protected AuditorDashboardShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}
}
