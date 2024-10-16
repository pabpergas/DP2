
package acme.features.manager.userStories;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S1.Project;
import acme.entities.S1.ProjectUserStories;
import acme.entities.S1.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoriesListByProjectService extends AbstractService<Manager, UserStory> {

	@Autowired
	private ManagerUserStoriesRepository repo;


	@Override
	public void authorise() {
		final int projectId = super.getRequest().getData("projectId", int.class);
		Project project = this.repo.findProjectById(projectId);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = project != null && project.getManager().getUserAccount().getId() == userAccountId || project != null && !project.isDraftMode();

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		Collection<UserStory> userStories;

		final int projectId = super.getRequest().getData("projectId", int.class);
		userStories = this.repo.findProjectUserStoriesByProjectId(projectId).stream().map(ProjectUserStories::getUserStories).collect(Collectors.toList());

		super.getBuffer().addData(userStories);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "estimatedCost");
		super.getResponse().addData(dataset);
	}
}
