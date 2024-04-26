
package acme.features.manager.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.managerDashboard;
import acme.roles.Manager;

@Controller
public class ManagerProjectDashboardController extends AbstractController<Manager, managerDashboard> {

	@Autowired
	private ManagerProjectDashboardShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);

	}
}
