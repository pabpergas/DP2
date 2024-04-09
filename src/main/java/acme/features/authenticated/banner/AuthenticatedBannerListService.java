
package acme.features.authenticated.banner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.groupal.Banner;

@Service
public class AuthenticatedBannerListService extends AbstractService<Authenticated, Banner> {

	@Autowired
	private AuthenticatedBannerRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Banner> sponsorShip;

		sponsorShip = this.repository.findAllBanners();
		super.getBuffer().addData(sponsorShip);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instantationMoment", "startDisplay", "endDisplay", "pictureLink", "slogan", "documentLink");
		super.getResponse().addData(dataset);
	}

}
