
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S4.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceListMineService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Invoice> sponsorShip;
		int sponsorId;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
		sponsorShip = this.repository.findInvoicesBySponsorId(sponsorId);
		super.getBuffer().addData(sponsorShip);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");
		super.getResponse().addData(dataset);
	}

}
