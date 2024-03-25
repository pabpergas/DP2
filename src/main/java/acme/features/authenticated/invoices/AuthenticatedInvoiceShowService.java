
package acme.features.authenticated.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S4.Invoice;

@Service
public class AuthenticatedInvoiceShowService extends AbstractService<Authenticated, Invoice> {

	@Autowired
	private AuthenticatedInvoiceRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);

	}

	@Override
	public void load() {
		Invoice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneInvoiceById(id);
		super.getBuffer().addData(object);

	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "sponsorShip.code", "code", "registrationTime", "dueDate", "quantity", "tax", "link");
		super.getResponse().addData(dataset);

	}

}