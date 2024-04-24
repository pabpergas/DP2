
package acme.features.sponsor.sponsorShip;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.Project;
import acme.entities.S4.Invoice;
import acme.entities.S4.SponsorShip;
import acme.entities.S4.SponsorShip.SponsorShipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorShipPublishService extends AbstractService<Sponsor, SponsorShip> {

	@Autowired
	private SponsorSponsorShipRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		SponsorShip sponsorShip;
		Sponsor sponsor;

		masterId = super.getRequest().getData("id", int.class);
		sponsorShip = this.repository.findOneSponsorShipById(masterId);
		sponsor = sponsorShip == null ? null : sponsorShip.getSponsor();
		status = sponsorShip != null && sponsorShip.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsor);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SponsorShip object;
		Sponsor sponsor;

		sponsor = this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new SponsorShip();
		object.setSponsor(sponsor);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final SponsorShip object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);
		super.bind(object, "code", "startDate", "endDate", "amount", "type", "contactEmail", "link");

		object.setProject(project);

	}

	@Override
	public void validate(final SponsorShip object) {
		assert object != null;

		Collection<Invoice> invoices;
		int id;

		LocalDateTime localDateTime = LocalDateTime.of(2201, 01, 01, 00, 00);
		Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
		Date limit = Date.from(instant);
		id = super.getRequest().getData("id", int.class);
		invoices = this.repository.findManyInvoicesBySponsorShipId(id);

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			SponsorShip existing;

			existing = this.repository.findOneSponsorShipByCodeAndDistinctId(object.getCode(), object.getId());
			super.state(existing == null, "code", "sponsor.sponsorShip.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			super.state(MomentHelper.isAfter(object.getStartDate(), object.getMoment()), "startDate", "sponsor.sponsorShip.error.too-close");
			super.state(MomentHelper.isAfter(limit, object.getStartDate()), "startDate", "sponsor.sponsorShip.error.startDate.limitSup");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			if (object.getStartDate() != null) {
				Date deadLine;
				Date startDate = object.getStartDate();
				Date startDateMinusOneSecond = Date.from(Instant.ofEpochMilli(startDate.getTime()).minus(Duration.ofSeconds(1)));
				deadLine = MomentHelper.deltaFromMoment(startDateMinusOneSecond, 30, ChronoUnit.DAYS);
				super.state(MomentHelper.isAfter(object.getEndDate(), deadLine), "endDate", "sponsor.sponsorShip.error.endDate");
			}
			super.state(MomentHelper.isAfter(limit, object.getEndDate()), "endDate", "sponsor.sponsorShip.error.endDate.limitSup");
		}

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			super.state(object.getAmount().getAmount() > 0 && object.getAmount().getAmount() <= 1000000, "amount", "sponsor.sponsorShip.error.amount");
			super.state(object.getAmount().getCurrency().equals("EUR") || object.getAmount().getCurrency().equals("GBD") || object.getAmount().getCurrency().equals("USD"), "amount", "sponsor.sponsorShip.error.amount.currency");

			Double totalAmount;

			totalAmount = invoices.stream().mapToDouble(Invoice::totalAmount).sum();
			System.out.println("totalAmount" + totalAmount);

			if (totalAmount != null)
				super.state(totalAmount.equals(object.getAmount().getAmount()), "amount", "sponsor.sponsorShip.error.total-amount");
			if (totalAmount == 0.0)
				super.state(object.getAmount().getAmount() == .0, "amount", "sponsor.sponsorShip.error.no-total-amount");
		}

	}

	@Override
	public void perform(final SponsorShip object) {
		assert object != null;

		Collection<Invoice> invoices;

		object.setDraftMode(false);
		invoices = this.repository.findManyInvoicesBySponsorShipId(object.getId());
		invoices.stream().forEach(invoice -> invoice.setDraftMode(false));

		this.repository.save(object);
	}

	@Override
	public void unbind(final SponsorShip object) {
		assert object != null;

		int sponsorId;
		Collection<Project> projects;
		SelectChoices choices;
		SelectChoices typeChoices;
		Dataset dataset;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
		projects = this.repository.findManyProjectsBySponsorId(sponsorId);
		choices = SelectChoices.from(projects, "title", object.getProject());
		typeChoices = SelectChoices.from(SponsorShipType.class, object.getType());

		dataset = super.unbind(object, "project", "code", "moment", "startDate", "endDate", "amount", "type", "contactEmail", "link", "draftMode");

		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		dataset.put("type", typeChoices.getSelected().getKey());
		dataset.put("types", typeChoices);
		super.getResponse().addData(dataset);

	}

}
