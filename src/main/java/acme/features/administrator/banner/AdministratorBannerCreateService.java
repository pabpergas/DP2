
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.groupal.Banner;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	@Autowired
	private AdministratorBannerRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;

		object = new Banner();
		object.setInstantationMoment(MomentHelper.getCurrentMoment());

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantationMoment", "startDisplay", "endDisplay", "pictureLink", "slogan", "documentLink");

	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDisplay"))
			super.state(MomentHelper.isAfter(object.getStartDisplay(), object.getInstantationMoment()), "startDisplay", "administrator.banner.error.startDisplay");
		if (!super.getBuffer().getErrors().hasErrors("endDisplay")) {
			Date deadLine;

			deadLine = MomentHelper.deltaFromMoment(object.getStartDisplay(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getEndDisplay(), deadLine), "endDisplay", "administrator.banner.error.endDisplay");

		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "instantationMoment", "startDisplay", "endDisplay", "pictureLink", "slogan", "documentLink");

		super.getResponse().addData(dataset);

	}

}
