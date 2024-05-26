
package acme.features.developer.trainingSessions;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.S3.Sessions;
import acme.entities.S3.Training;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionCreateService extends AbstractService<Developer, Sessions> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Training trainingModule;

		masterId = super.getRequest().getData("masterId", int.class);
		trainingModule = this.repository.findOneTMById(masterId);
		status = trainingModule != null && trainingModule.isDraftMode() && super.getRequest().getPrincipal().hasRole(trainingModule.getDeveloper());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sessions session;
		int masterId;
		Training module;

		masterId = super.getRequest().getData("masterId", int.class);
		module = this.repository.findOneTMById(masterId);

		session = new Sessions();
		session.setTraining(module);

		super.getBuffer().addData(session);
	}

	@Override
	public void bind(final Sessions object) {
		assert object != null;

		super.bind(object, "code", "startDate", "endDate", "location", "instructor", "contactEmail", "link");
	}

	@Override
	public void validate(final Sessions object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sessions existing;

			existing = this.repository.findOneTSByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "developer.training-session.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			Training module;
			int masterId;

			masterId = super.getRequest().getData("masterId", int.class);
			module = this.repository.findOneTMById(masterId);
			super.state(MomentHelper.isAfter(object.getStartDate(), module.getMoment()), "startDate", "developer.training-session.form.error.creation-moment-invalid");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			Date minimumEnd;

			minimumEnd = MomentHelper.deltaFromMoment(object.getStartDate(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getEndDate(), minimumEnd), "endDate", "developer.training-session.form.error.too-close");
		}

	}

	@Override
	public void perform(final Sessions object) {
		assert object != null;

		object.setDraftMode(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Sessions object) {

		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startDate", "endDate", "location", "instructor", "email", "link", "draftMode");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().addData(dataset);
	}

}
