
package acme.features.sponsor.sponsorShip;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S4.SponsorShip;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorShipListMineService extends AbstractService<Sponsor, SponsorShip> {

	@Autowired
	private SponsorSponsorShipRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<SponsorShip> sponsorShip;
		int sponsorId;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
		sponsorShip = this.repository.findSponsorShipBySponsorId(sponsorId);
		super.getBuffer().addData(sponsorShip);
	}

	@Override
	public void unbind(final SponsorShip object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "amount", "type", "contactEmail", "link");
		super.getResponse().addData(dataset);
	}

}
