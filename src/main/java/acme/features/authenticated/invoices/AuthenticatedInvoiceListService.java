
package acme.features.authenticated.invoices;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S4.Invoice;
import acme.roles.Sponsor;

@Service
public class AuthenticatedInvoiceListService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private AuthenticatedInvoiceRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Invoice> sponsorShip;

		sponsorShip = this.repository.findAllInvoice();
		super.getBuffer().addData(sponsorShip);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "sponsorShip.code", "code", "registrationTime", "dueDate", "quantity", "tax", "link");
		super.getResponse().addData(dataset);
	}

}
