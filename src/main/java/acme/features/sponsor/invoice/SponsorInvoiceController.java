
package acme.features.sponsor.invoice;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.S4.Invoice;
import acme.roles.Sponsor;

@Controller
public class SponsorInvoiceController extends AbstractController<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceListMineService	listMineService;

	@Autowired
	private SponsorInvoiceShowService		showService;

	@Autowired
	private SponsorInvoiceCreateService		createService;

	@Autowired
	private SponsorInvoiceUpdateService		updateService;

	@Autowired
	private SponsorInvoiceDeleteService		deleteService;

	@Autowired
	private SponsorInvoicePublishService	publishService;


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
