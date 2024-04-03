
package acme.features.authenticated.invoices;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.S4.Invoice;
import acme.roles.Sponsor;

@Controller
public class AuthenticatedInvoiceController extends AbstractController<Sponsor, Invoice> {

	@Autowired
	private AuthenticatedInvoiceListService	listService;

	@Autowired
	private AuthenticatedInvoiceShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);

	}

}
