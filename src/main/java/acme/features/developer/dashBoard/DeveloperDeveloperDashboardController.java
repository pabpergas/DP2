
package acme.features.developer.dashBoard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Controller
public class DeveloperDeveloperDashboardController extends AbstractController<Developer, DeveloperDashboard> {

	@Autowired
	private DeveloperDeveloperDashboardService showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);

	}

}
