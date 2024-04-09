
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
public class SponsorSponsorShipDeleteService extends AbstractService<Sponsor, SponsorShip> {

	@Autowired
	private SponsorSponsorShipRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		SponsorShip sponsorShip;
		Sponsor sponsor;

		masterId = super.getRequest().getData("id", int.class);
		System.out.println("hola1" + masterId);

		sponsorShip = this.repository.findOneSponsorShipById(masterId);
		sponsor = sponsorShip == null ? null : sponsorShip.getSponsor();
		System.out.println("hola3" + sponsor + sponsorShip);

		status = sponsorShip != null && sponsorShip.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsor);
		System.out.println("hola2" + status);
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
		super.bind(object, "code", "moment", "startDate", "endDate", "amount", "type", "contactEmail", "link");

		object.setProject(project);

	}

	@Override
	public void validate(final SponsorShip object) {
		assert object != null;
	}

	@Override
	public void perform(final SponsorShip object) {
		assert object != null;

		this.repository.delete(object);
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