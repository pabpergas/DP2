
package acme.features.developer.trainingModules;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.Project;
import acme.entities.S3.Training;
import acme.entities.S3.Training.Difficulty;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleListMineService extends AbstractService<Developer, Training> {

	@Autowired
	private DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Training> modules;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		modules = this.repository.findAllTMByDevId(principal.getActiveRoleId());

		super.getBuffer().addData(modules);
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

		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);

	}

}
