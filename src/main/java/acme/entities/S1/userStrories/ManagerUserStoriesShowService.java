
package acme.entities.S1.userStrories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S1.project.Project;
import acme.roles.Manager;

@Service
public class ManagerUserStoriesShowService extends AbstractService<Manager, UserStories> {

	@Autowired
	private ManagerUserStoriesRepository repo;


	@Override
	public void authorise() {
		boolean status;
		int usId;
		Project project;

		usId = super.getRequest().getData("id", int.class);
		project = this.repo.findOneProjectByUserStoryId(usId);

		status = project != null && (project.getDraftMode() == true || super.getRequest().getPrincipal().hasRole(project.getManager()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStories object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final UserStories object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "description", "acceptanceCriteria", "estimatedCost", "proirity", "link");
		dataset.put("masterId", object.getProject().getId());

		super.getResponse().addData(dataset);
	}

}
