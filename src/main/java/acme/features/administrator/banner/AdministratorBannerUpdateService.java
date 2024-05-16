
package acme.features.administrator.banner;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	@Autowired
	private AdministratorBannerRepository	repository;

	private String							endDisplay	= "endDisplay";


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
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "startDisplay", this.endDisplay, "pictureLink", "slogan", "documentLink");

	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		LocalDateTime localDateTime = LocalDateTime.of(2201, 01, 01, 00, 00);
		Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
		Date limitEndDisplay = Date.from(instant);
		Date limitStartDisplay = MomentHelper.deltaFromMoment(limitEndDisplay, -7, ChronoUnit.DAYS);

		if (!super.getBuffer().getErrors().hasErrors("startDisplay")) {
			super.state(MomentHelper.isAfter(object.getStartDisplay(), object.getInstantationMoment()), "startDisplay", "administrator.banner.error.startDisplay");
			super.state(MomentHelper.isAfter(limitStartDisplay, object.getStartDisplay()), "startDisplay", "administrator.banner.error.startDisplay.limitSup");
		}

		if (!super.getBuffer().getErrors().hasErrors(this.endDisplay)) {
			Date deadLine;
			Date startDisplay = object.getStartDisplay();

			if (object.getStartDisplay() != null) {
				Date startDateMinusOneSecond = Date.from(Instant.ofEpochMilli(startDisplay.getTime()).minus(Duration.ofSeconds(1)));
				deadLine = MomentHelper.deltaFromMoment(startDateMinusOneSecond, 7, ChronoUnit.DAYS);
				super.state(MomentHelper.isAfter(object.getEndDisplay(), deadLine), this.endDisplay, "administrator.banner.error.endDisplay");
			}

			super.state(MomentHelper.isAfter(limitEndDisplay, object.getEndDisplay()), this.endDisplay, "administrator.banner.error.endDisplay.limitSup");

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
		dataset = super.unbind(object, "instantationMoment", "startDisplay", this.endDisplay, "pictureLink", "slogan", "documentLink");

		super.getResponse().addData(dataset);

	}

}
