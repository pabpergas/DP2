
package acme.features.sponsor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.S4.SponsorShip;
import acme.roles.Sponsor;

@Controller
public class AuthenticatedSponsorShipController extends AbstractController<Sponsor, SponsorShip> {

	@Autowired
	private AuthenticatedSponsorShipListService	listService;

	@Autowired
	private AuthenticatedSponsorShipShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);

	}

}
