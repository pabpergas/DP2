
package acme.features.authenticated.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.S4.SponsorShip;

@Controller
public class AuthenticatedSponsorShipController extends AbstractController<Authenticated, SponsorShip> {

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
