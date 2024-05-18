
package acme.features.sponsor.invoice;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
public class SponsorInvoicePublishService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int invoiceId;
		SponsorShip sponsorship;
		Invoice object;

		invoiceId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorShipByInvoiceId(invoiceId);
		object = this.repository.findOneInvoiceById(invoiceId);

		//No se puede comprobar el camino donde invoice NO este en draftMode y sponsorShip SI lo este
		status = sponsorship != null && object.isDraftMode() && sponsorship.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor());

		super.getResponse().setAuthorised(status);
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
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");

	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		String sponsorShipCurrency = object.getSponsorShip().getAmount().getCurrency();
		LocalDateTime localDateTime = LocalDateTime.of(2201, 01, 01, 00, 00);
		Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
		Date limit = Date.from(instant);

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;

			existing = this.repository.findOneInvoiceByCodeAndDistinctId(object.getCode(), object.getId());
			super.state(existing == null, "code", "sponsor.invoice.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			Date deadLine;
			Date registrationTime = object.getRegistrationTime();
			Date registrationTimeMinusOneSecond = Date.from(Instant.ofEpochMilli(registrationTime.getTime()).minus(Duration.ofSeconds(1)));
			deadLine = MomentHelper.deltaFromMoment(registrationTimeMinusOneSecond, 30, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getDueDate(), deadLine), "dueDate", "sponsor.invoice.error.dueDate");
			super.state(MomentHelper.isAfter(limit, object.getDueDate()), "dueDate", "sponsor.invoice.error.dueDate.limitSup");

		}
		if (!super.getBuffer().getErrors().hasErrors("quantity")) {

			super.state(object.getQuantity().getAmount() > 0 && object.getQuantity().getAmount() <= 500000, "quantity", "sponsor.invoice.error.quantity");
			super.state(object.getQuantity().getCurrency().equals(sponsorShipCurrency), "quantity", "sponsor.invoice.error.quantity.currency");
		}

	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "draftMode");
		dataset.put("masterId", object.getSponsorShip().getId());

		super.getResponse().addData(dataset);

	}

}
