
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
		Collection<Invoice> invoices;
		int sponsorShipId;

		sponsorShipId = super.getRequest().getData("masterId", int.class);
		invoices = this.repository.findInvoicesBySponsorShipId(sponsorShipId);
		super.getBuffer().addData(invoices);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		int sponsorShipId;
		Dataset dataset;

		sponsorShipId = super.getRequest().getData("masterId", int.class);

		dataset = super.unbind(object, "code", "registrationTime", "quantity", "tax");
		super.getResponse().addGlobal("masterId", sponsorShipId);
		super.getResponse().addData(dataset);
	}

}
