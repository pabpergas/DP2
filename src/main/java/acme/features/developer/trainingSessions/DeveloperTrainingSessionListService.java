
package acme.features.developer.trainingSessions;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S3.Sessions;
import acme.entities.S3.Training;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionListService extends AbstractService<Developer, Sessions> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Training module;

		masterId = super.getRequest().getData("masterId", int.class);
		module = this.repository.findOneTMById(masterId);
		status = module != null && (!module.isDraftMode() || super.getRequest().getPrincipal().hasRole(module.getDeveloper()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Sessions> sessions;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		sessions = this.repository.findAllTSByMasterId(masterId);

		super.getBuffer().addData(sessions);
	}

	@Override
	public void unbind(final Sessions object) {

		assert object != null;

		Dataset dataset;
		System.out.println(object.toString());
		dataset = super.unbind(object, "code", "startDate", "endDate", "location", "instructor", "email", "link", "draftMode");

		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<Sessions> objects) {
		assert objects != null;

		int masterId;
		Training module;
		final boolean showCreate;

		masterId = super.getRequest().getData("masterId", int.class);
		module = this.repository.findOneTMById(masterId);
		showCreate = module.isDraftMode() && super.getRequest().getPrincipal().hasRole(module.getDeveloper());

		super.getResponse().addGlobal("masterId", masterId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}
