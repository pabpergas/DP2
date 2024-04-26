
package acme.features.sponsor.dashBoard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Controller
public class SponsorSponsorDashboardController extends AbstractController<Sponsor, SponsorDashboard> {

	@Autowired
	private SponsorSponsorDashboardShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);

	}

}
