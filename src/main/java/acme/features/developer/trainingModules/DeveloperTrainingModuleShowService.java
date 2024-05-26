
package acme.features.developer.trainingModules;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.Project;
import acme.entities.S3.Training;
import acme.entities.S3.Training.Difficulty;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleShowService extends AbstractService<Developer, Training> {

	@Autowired
	private DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		Boolean status;
		int masterId;
		Training tm;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		tm = this.repository.findOneTMById(masterId);
		developer = tm == null ? null : tm.getDeveloper();
		status = super.getRequest().getPrincipal().hasRole(developer) || tm != null && !tm.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Training object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTMById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Training object) {
		assert object != null;
		SelectChoices choices;
		SelectChoices projectsChoices;
		Collection<Project> projects;

		Dataset dataset;
		choices = SelectChoices.from(Difficulty.class, object.getDifficulty());
		projects = this.repository.findAllProjects();
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code", "moment", "details", "difficulty", "updateMoment", "link", "estimatedTotalTime", "draftMode", "project");
		dataset.put("difficulty", choices);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);

	}

}
