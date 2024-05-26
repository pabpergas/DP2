
package acme.features.developer.trainingSessions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S3.Sessions;
import acme.entities.S3.Training;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionShowService extends AbstractService<Developer, Sessions> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int sessionId;
		Training module;

		sessionId = super.getRequest().getData("id", int.class);
		module = this.repository.findOneTMByTSId(sessionId);
		status = module != null && (!module.isDraftMode() || super.getRequest().getPrincipal().hasRole(module.getDeveloper()));

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Sessions session;
		int id;

		id = super.getRequest().getData("id", int.class);
		session = this.repository.findOneTSById(id);

		super.getBuffer().addData(session);
	}

	@Override
	public void unbind(final Sessions object) {

		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startDate", "endDate", "location", "instructor", "email", "link", "draftMode");
		dataset.put("masterId", object.getTraining().getId());

		super.getResponse().addData(dataset);
	}

}
