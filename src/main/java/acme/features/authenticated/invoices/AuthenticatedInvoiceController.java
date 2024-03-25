
package acme.features.authenticated.invoices;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.S4.Invoice;

@Controller
public class AuthenticatedInvoiceController extends AbstractController<Authenticated, Invoice> {

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
