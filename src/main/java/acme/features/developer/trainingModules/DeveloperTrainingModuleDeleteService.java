
package acme.features.developer.trainingModules;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.Project;
import acme.entities.S3.Sessions;
import acme.entities.S3.Training;
import acme.entities.S3.Training.Difficulty;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleDeleteService extends AbstractService<Developer, Training> {

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
		status = tm != null && tm.isDraftMode() && super.getRequest().getPrincipal().hasRole(developer);

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
	public void bind(final Training object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.unbind(object, "code", "moment", "details", "difficulty", "updateMoment", "link", "estimatedTotalTime", "draftMode", "project");
		object.setProject(project);

	}

	@Override
	public void validate(final Training object) {
		assert object != null;
	}

	@Override
	public void perform(final Training object) {
		assert object != null;

		Collection<Sessions> sessions;

		sessions = this.repository.findAllTSByTMId(object.getId());
		this.repository.deleteAll(sessions);
		this.repository.delete(object);
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
		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "estimatedTotalTime", "draftMode", "project");
		dataset.put("difficulty", choices);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);

	}

}
