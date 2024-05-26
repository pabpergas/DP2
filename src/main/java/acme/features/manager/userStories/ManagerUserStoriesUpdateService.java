
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
public class ManagerUserStoriesUpdateService extends AbstractService<Manager, UserStories> {

	@Autowired
	private ManagerUserStoriesRepository repo;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		UserStories us = this.repo.findUserStoryById(id);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccount = principal.getAccountId();

		final boolean status = us != null && principal.hasRole(Manager.class) && us.isDraftMode() && us.getManager().getUserAccount().getId() == userAccount;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		UserStories userStory = this.repo.findUserStoryById(id);

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
