
package acme.features.sponsor.sponsorShip;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.Project;
import acme.entities.S4.SponsorShip;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorShipShowService extends AbstractService<Sponsor, SponsorShip> {

	@Autowired
	private SponsorSponsorShipRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);

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
	public void unbind(final SponsorShip object) {
		assert object != null;

		int sponsorId;
		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
		projects = this.repository.findManyProjectsBySponsorId(sponsorId);
		choices = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "amount", "type", "contactEmail", "link");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		super.getResponse().addData(dataset);

	}

}
