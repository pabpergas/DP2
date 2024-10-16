
package acme.features.manager.userStories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.UserStory;
import acme.entities.S1.UserStory.priorityUserStories;
import acme.roles.Manager;

@Service
public class ManagerUserStoriesDeleteService extends AbstractService<Manager, UserStory> {

	@Autowired
	private ManagerUserStoriesRepository repo;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		UserStory us = this.repo.findUserStoryById(id);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccount = principal.getAccountId();

		final boolean status = us != null && us.isDraftMode() && principal.hasRole(Manager.class) && us.getManager().getUserAccount().getId() == userAccount;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		UserStory userStory = this.repo.findUserStoryById(id);

		super.getBuffer().addData(userStory);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;

		super.bind(object, "title", "description", "acceptanceCriteria", "estimatedCost", "priority", "link");
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		this.repo.delete(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		SelectChoices choices = SelectChoices.from(priorityUserStories.class, object.getPriority());

		Dataset dataset = super.unbind(object, "title", "description", "acceptanceCriteria", "estimatedCost", "priority", "link", "draftMode");

		dataset.put("priority", choices);

		super.getResponse().addData(dataset);
	}
}
