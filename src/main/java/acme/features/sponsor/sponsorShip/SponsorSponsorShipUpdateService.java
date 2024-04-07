
package acme.features.sponsor.sponsorShip;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.Project;
import acme.entities.S4.SponsorShip;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorShipUpdateService extends AbstractService<Sponsor, SponsorShip> {

	@Autowired
	private SponsorSponsorShipRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		SponsorShip object;
		Sponsor sponsor;

		sponsor = this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new SponsorShip();
		object.setDraftMode(true);
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
		super.bind(object, "code", "moment", "startDate", "endDate", "amount", "type", "contactEmail", "link");

		object.setProject(project);

	}

	@Override
	public void validate(final SponsorShip object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			SponsorShip existing;

			existing = this.repository.findOneSponsorShipByCodeAndDistinctId(object.getCode(), object.getId());
			super.state(existing == null, "code", "sponsor.sponsorShip.error.duplicated");
		}
		//Falta validar el moment
		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(MomentHelper.isAfter(object.getStartDate(), object.getMoment()), "startDate", "sponsor.sponsorShip.error.too-close");

	}
	@Override
	public void perform(final SponsorShip object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final SponsorShip object) {
		assert object != null;

		int sponsorId;
		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
		projects = this.repository.findManyProjectsBySponsorId(sponsorId);
		choices = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "project", "code", "moment", "startDate", "endDate", "amount", "type", "contactEmail", "link", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		super.getResponse().addData(dataset);

	}

}
