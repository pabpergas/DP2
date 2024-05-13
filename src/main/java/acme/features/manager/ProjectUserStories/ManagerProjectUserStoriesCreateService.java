
package acme.features.manager.ProjectUserStories;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.Project;
import acme.entities.S1.ProjectUserStories;
import acme.entities.S1.UserStories;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoriesCreateService extends AbstractService<Manager, ProjectUserStories> {

	@Autowired
	private ManagerProjectUserStoriesRepository repo;


	@Override
	public void authorise() {

		final int projectId = super.getRequest().getData("projectId", int.class);
		Project project = this.repo.findProjectById(projectId);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean status = project != null && principal.hasRole(Manager.class) && project.getManager().getUserAccount().getId() == userAccountId;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int projectId = super.getRequest().getData("projectId", int.class);
		Project project = this.repo.findProjectById(projectId);

		ProjectUserStories object = new ProjectUserStories();
		object.setProject(project);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProjectUserStories object) {
		assert object != null;

		super.bind(object, "userStories");
	}

	@Override
	public void validate(final ProjectUserStories object) {
		assert object != null;
		final int projectId = super.getRequest().getData("projectId", int.class);

		if (!super.getBuffer().getErrors().hasErrors("userStory")) {
			final boolean duplicatedUS = this.repo.findProjectUserStoriesByProjectId(projectId).stream().anyMatch(a -> a.getUserStories().equals(object.getUserStories()));

			super.state(!duplicatedUS, "userStories", "manager.project.form.error.duplicated-userStory");
		}

	}

	@Override
	public void perform(final ProjectUserStories object) {
		assert object != null;

		this.repo.save(object);
	}

	@Override
	public void unbind(final ProjectUserStories object) {
		assert object != null;
		final int projectId = super.getRequest().getData("projectId", int.class);

		int managerID = object.getProject().getManager().getUserAccount().getId();
		Collection<UserStories> userStories = this.repo.findUserStoriesByManagerId(managerID).stream() //
			.filter(us -> !this.repo.findProjectUserStoriesByProjectId(projectId).stream().map(ProjectUserStories::getUserStories).anyMatch(us2 -> us2.equals(us))).toList();

		SelectChoices choices = SelectChoices.from(userStories, "title", object.getUserStories());

		Dataset dataset = super.unbind(object, "userStories");

		dataset.put("userStories", choices);
		dataset.put("projectId", projectId);

		super.getResponse().addData(dataset);
	}
}
