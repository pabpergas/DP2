
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
public class SponsorInvoiceCreateService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Invoice object;
		Sponsor sponsor;

		sponsor = this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Invoice();
		object.setDraftMode(true);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		int sponsorShipId;
		SponsorShip sponsorShip;

		sponsorShipId = super.getRequest().getData("sponsorShip", int.class);
		sponsorShip = this.repository.findOneSponsorShipById(sponsorShipId);
		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");

		object.setSponsorShip(sponsorShip);
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;

			existing = this.repository.findOneInvoiceByCode(object.getCode());
			super.state(existing == null, "code", "sponsor.sponsorShip.error.duplicated");
		}
		//Falta validar el moment
		//if (!super.getBuffer().getErrors().hasErrors("startDate"))
		//super.state(MomentHelper.isAfter(object.getStartDate(), object.getMoment()), "startDate", "sponsor.sponsorShip.error.too-close");

	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		this.repository.save(object);
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
