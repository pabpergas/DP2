
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S4.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceListAllService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Invoice> invoices;
		int sponsorId;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		invoices = this.repository.findAllInvoicesBySponsorId(sponsorId);

		super.getBuffer().addData(invoices);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "sponsorShip.code", "dueDate");
		super.getResponse().addData(dataset);
	}
}
