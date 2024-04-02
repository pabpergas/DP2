
package acme.features.authenticated.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S4.SponsorShip;
import acme.roles.Sponsor;

@Service
public class AuthenticatedSponsorShipShowService extends AbstractService<Sponsor, SponsorShip> {

	@Autowired
	private AuthenticatedSponsorShipRepository repository;


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

		Dataset dataset;

		dataset = super.unbind(object, "project.title", "code", "moment", "startDate", "endDate", "amount", "type", "contactEmail", "link");
		super.getResponse().addData(dataset);

	}

}
