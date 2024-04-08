
package acme.entities.S1.userStrories;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S1.project.Project;
import acme.roles.Manager;

@Service
public class ManagerUserStoriesListService extends AbstractService<Manager, UserStories> {

	@Autowired
	private ManagerUserStoriesRepository repo;


	@Override
	public void authorise() {
		boolean status;
		int usId;
		Project project;

		usId = super.getRequest().getData("id", int.class);
		project = this.repo.findOneProjectByUserStoryId(usId);

		status = project != null && (project.getDraftMode() == false || super.getRequest().getPrincipal().hasRole(project.getManager()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<UserStories> objects;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repo.findManylUserStoriesByProjectId(masterId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final UserStories object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "estimatedCost");
		super.getResponse().addData(dataset);
	}
}
