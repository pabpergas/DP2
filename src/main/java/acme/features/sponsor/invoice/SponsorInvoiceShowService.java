
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S4.Invoice;
import acme.entities.S4.SponsorShip;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceShowService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repository;


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

		int sponsorId;
		Collection<SponsorShip> sponsorShips;
		SelectChoices choices;
		Dataset dataset;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
		sponsorShips = this.repository.findManySponsorShipsBySponsorId(sponsorId);
		choices = SelectChoices.from(sponsorShips, "code", object.getSponsorShip());

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");

		dataset.put("sponsorShip", choices.getSelected().getKey());
		dataset.put("sponsorShips", choices);

		super.getResponse().addData(dataset);

	}

}
