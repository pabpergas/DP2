
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
public class SponsorSponsorShipUpdateService extends AbstractService<Sponsor, SponsorShip> {

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
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSponsorShipById(id);

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
		String currency = null;
		int id;
		id = super.getRequest().getData("id", int.class);

		invoices = this.repository.findManyInvoicesBySponsorShipId(id);

		LocalDateTime localDateTime = LocalDateTime.of(2201, 01, 01, 00, 00);
		Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
		Date limitEndDate = Date.from(instant);
		Date limitStartDate = MomentHelper.deltaFromMoment(limitEndDate, -30, ChronoUnit.DAYS);

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			SponsorShip existing;

			existing = this.repository.findOneSponsorShipByCodeAndDistinctId(object.getCode(), object.getId());
			super.state(existing == null, "code", "sponsor.sponsorShip.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			super.state(MomentHelper.isAfter(object.getStartDate(), object.getMoment()), "startDate", "sponsor.sponsorShip.error.too-close");
			super.state(MomentHelper.isAfter(limitStartDate, object.getStartDate()), "startDate", "sponsor.sponsorShip.error.startDate.limitSup");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			if (object.getStartDate() != null) {
				Date deadLine;
				Date startDate = object.getStartDate();
				Date startDateMinusOneSecond = Date.from(Instant.ofEpochMilli(startDate.getTime()).minus(Duration.ofSeconds(1)));
				deadLine = MomentHelper.deltaFromMoment(startDateMinusOneSecond, 30, ChronoUnit.DAYS);
				super.state(MomentHelper.isAfter(object.getEndDate(), deadLine), "endDate", "sponsor.sponsorShip.error.endDate");
			}
			super.state(MomentHelper.isAfter(limitEndDate, object.getEndDate()), "endDate", "sponsor.sponsorShip.error.endDate.limitSup");

		}
		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			super.state(object.getAmount().getAmount() > 0 && object.getAmount().getAmount() <= 1000000, "amount", "sponsor.sponsorShip.error.amount");
			if (!invoices.isEmpty()) {
				currency = invoices.stream().toList().get(0).getQuantity().getCurrency();
				super.state(object.getAmount().getCurrency().equals(currency), "amount", "sponsor.sponsorShip.error.amount.currency.invoices");

			}
		}
	}

	@Override
	public void perform(final SponsorShip object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final SponsorShip object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		SelectChoices typeChoices;
		Dataset dataset;

		projects = this.repository.findAllProjects();

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
