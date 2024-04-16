
package acme.features.sponsor.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
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
		SponsorShip sponsorShip;
		int sponsorShipId;

		sponsorShipId = super.getRequest().getData("masterId", int.class);
		sponsorShip = this.repository.findOneSponsorShipById(sponsorShipId);

		object = new Invoice();
		object.setDraftMode(true);
		object.setSponsorShip(sponsorShip);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		int sponsorShipId;
		SponsorShip sponsorShip;

		sponsorShipId = super.getRequest().getData("masterId", int.class);
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
		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "draftMode");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().addData(dataset);

	}

}
