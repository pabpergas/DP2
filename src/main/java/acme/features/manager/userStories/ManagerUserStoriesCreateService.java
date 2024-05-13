
package acme.features.manager.userStories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.UserStories;
import acme.entities.S1.UserStories.priorityUserStories;
import acme.roles.Manager;

@Service
public class ManagerUserStoriesCreateService extends AbstractService<Manager, UserStories> {

	@Autowired
	private ManagerUserStoriesRepository repo;


	@Override
	public void authorise() {
		final Principal principal = super.getRequest().getPrincipal();

		final boolean authorise = principal.hasRole(Manager.class);

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		UserStories userStory = new UserStories();
		final int id = super.getRequest().getPrincipal().getActiveRoleId();
		userStory.setManager(this.repo.findManagerById(id));

		super.getBuffer().addData(userStory);
	}

	@Override
	public void bind(final UserStories object) {
		assert object != null;

		super.bind(object, "title", "description", "acceptanceCriteria", "estimatedCost", "priority", "link");
	}

	@Override
	public void validate(final UserStories object) {
		assert object != null;

	}

	@Override
	public void perform(final UserStories object) {
		assert object != null;

		this.repo.save(object);
	}

	@Override
	public void unbind(final UserStories object) {
		assert object != null;

		SelectChoices choices = SelectChoices.from(priorityUserStories.class, object.getPriority());

		Dataset dataset = super.unbind(object, "title", "description", "acceptanceCriteria", "estimatedCost", "priority", "link", "draftMode");

		dataset.put("priority", choices);

		super.getResponse().addData(dataset);
	}

}
