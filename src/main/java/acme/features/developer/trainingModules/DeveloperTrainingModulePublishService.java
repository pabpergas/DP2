
package acme.features.developer.trainingModules;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.Project;
import acme.entities.S3.Sessions;
import acme.entities.S3.Training;
import acme.entities.S3.Training.Difficulty;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModulePublishService extends AbstractService<Developer, Training> {

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

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Training existing;

			existing = this.repository.findOneTMByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "developer.training-module.form.error.duplicated");
		}

		if (object.getUpdateMoment() != null && !super.getBuffer().getErrors().hasErrors("updateMoment"))
			super.state(MomentHelper.isAfter(object.getUpdateMoment(), object.getMoment()), "updateMoment", "developer.training-module.form.error.update-date-not-valid");

		{
			Collection<Sessions> sessions;
			int totalSessions;

			sessions = this.repository.findAllTSByTMId(object.getId());
			totalSessions = sessions.size();
			super.state(totalSessions >= 1, "*", "developer.training-module.form.error.not-enough-training-sessions");
			boolean published = sessions.stream().allMatch(c -> c.isDraftMode() == false);
			if (!published)
				super.state(published, "*", "developer.training-module.form.error.all-training-sessions-must-be-published");
		}
	}

	@Override
	public void perform(final Training object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
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
