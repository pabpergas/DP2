
package acme.features.authenticated.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.groupal.Banner;

@Service
public class AuthenticatedBannerShowService extends AbstractService<Authenticated, Banner> {

	@Autowired
	private AuthenticatedBannerRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);

	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);
		super.getBuffer().addData(object);

	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "instantationMoment", "startDisplay", "endDisplay", "pictureLink", "slogan", "documentLink");
		super.getResponse().addData(dataset);

	}

}