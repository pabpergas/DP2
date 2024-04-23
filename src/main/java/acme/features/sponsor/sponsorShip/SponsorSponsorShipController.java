
package acme.features.sponsor.sponsorShip;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.S4.SponsorShip;
import acme.roles.Sponsor;

@Controller
public class SponsorSponsorShipController extends AbstractController<Sponsor, SponsorShip> {

	@Autowired
	private SponsorSponsorShipListMineService	listMineService;

	@Autowired
	private SponsorSponsorShipShowService		showService;

	@Autowired
	private SponsorSponsorShipCreateService		createService;

	@Autowired
	private SponsorSponsorShipUpdateService		updateService;

	@Autowired
	private SponsorSponsorShipDeleteService		deleteService;

	@Autowired
	private SponsorSponsorShipPublishService	publishService;


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
