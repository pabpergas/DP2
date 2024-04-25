
package acme.features.manager.ProjectUserStories;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S1.Project;
import acme.entities.S1.ProjectUserStories;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoriesListService extends AbstractService<Manager, ProjectUserStories> {

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
		Collection<ProjectUserStories> pus = this.repo.findProjectUserStoriesByProjectId(projectId);

		super.getBuffer().addData(pus);
	}

	@Override
	public void unbind(final ProjectUserStories object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "userStories");

		super.getResponse().addData(dataset);
	}

}
