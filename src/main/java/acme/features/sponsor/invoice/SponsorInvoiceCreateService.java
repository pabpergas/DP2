
package acme.features.sponsor.invoice;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
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
		boolean status;
		int masterId;
		SponsorShip sponsorship;

		masterId = super.getRequest().getData("masterId", int.class);
		sponsorship = this.repository.findOneSponsorShipById(masterId);
		status = sponsorship != null && (!sponsorship.isDraftMode() || super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		SponsorShip sponsorShip;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		sponsorShip = this.repository.findOneSponsorShipById(masterId);

		object = new Invoice();
		object.setRegistrationTime(MomentHelper.getCurrentMoment());
		object.setSponsorShip(sponsorShip);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");

	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;

			existing = this.repository.findOneInvoiceByCode(object.getCode());
			super.state(existing == null, "code", "sponsor.invoice.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			Date deadLine;
			Date registrationTime = object.getRegistrationTime();
			Date registrationTimeMinusOneSecond = Date.from(Instant.ofEpochMilli(registrationTime.getTime()).minus(Duration.ofSeconds(1)));
			deadLine = MomentHelper.deltaFromMoment(registrationTimeMinusOneSecond, 30, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getDueDate(), deadLine), "dueDate", "sponsor.invoice.error.dueDate");
		}
		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getQuantity().getAmount() > 0 && object.getQuantity().getAmount() <= 1000000, "quantity", "sponsor.invoice.error.quantity");

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

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));
		dataset.put("draftMode", object.getSponsorShip().isDraftMode());

		super.getResponse().addData(dataset);

	}

}
