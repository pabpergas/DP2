
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
import acme.entities.S4.SponsorShip;
import acme.entities.S4.SponsorShip.SponsorShipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorShipCreateService extends AbstractService<Sponsor, SponsorShip> {

	@Autowired
	private SponsorSponsorShipRepository repository;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SponsorShip object;
		Sponsor sponsor;

		sponsor = this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new SponsorShip();
		object.setDraftMode(true);
		object.setSponsor(sponsor);
		object.setMoment(MomentHelper.getCurrentMoment());
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

		LocalDateTime localDateTime = LocalDateTime.of(2201, 01, 01, 00, 00);
		Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
		Date limitEndDate = Date.from(instant);
		Date limitStartDate = MomentHelper.deltaFromMoment(limitEndDate, -30, ChronoUnit.DAYS);

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			SponsorShip existing;
			existing = this.repository.findOneSponsorShipByCode(object.getCode());
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
			super.state(object.getAmount().getCurrency().equals("EUR") || object.getAmount().getCurrency().equals("GBP") || object.getAmount().getCurrency().equals("USD"), "amount", "sponsor.sponsorShip.error.amount.currency");

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

		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "amount", "type", "contactEmail", "link", "draftMode");

		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		dataset.put("type", typeChoices.getSelected().getKey());
		dataset.put("types", typeChoices);

		super.getResponse().addData(dataset);

	}

}
