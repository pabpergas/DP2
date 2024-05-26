
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.services.AbstractService;
import acme.entities.groupal.Banner;

@Service
public class AdministratorBannerDeleteService extends AbstractService<Administrator, Banner> {

	@Autowired
	private AdministratorBannerRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Banner banner;
		masterId = super.getRequest().getData("id", int.class);
		banner = this.repository.findOneBannerById(masterId);
		status = banner != null;
		super.getResponse().setAuthorised(status);
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
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "startDisplay", "endDisplay", "pictureLink", "slogan", "documentLink");

	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Banner object) {
		/*
		 * assert object != null;
		 * 
		 * Dataset dataset;
		 * dataset = super.unbind(object, "instantationMoment", "startDisplay", "endDisplay", "pictureLink", "slogan", "documentLink");
		 * 
		 * super.getResponse().addData(dataset);
		 */
	}

}
