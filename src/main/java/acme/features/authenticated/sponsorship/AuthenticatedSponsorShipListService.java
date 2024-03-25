
package acme.features.authenticated.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S4.SponsorShip;

@Service
public class AuthenticatedSponsorShipListService extends AbstractService<Authenticated, SponsorShip> {

	@Autowired
	private AuthenticatedSponsorShipRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<SponsorShip> sponsorShip;

		sponsorShip = this.repository.findAllSponsorShip();
		super.getBuffer().addData(sponsorShip);
	}

	@Override
	public void unbind(final SponsorShip object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "project.title", "code", "moment", "startDate", "endDate", "amount", "type", "contactEmail", "link", "sponsor.name");
		super.getResponse().addData(dataset);
	}

}
