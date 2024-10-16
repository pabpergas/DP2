
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
public class ManagerUserStoriesShowService extends AbstractService<Manager, UserStory> {

	@Autowired
	private ManagerUserStoriesRepository repo;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		UserStory us = this.repo.findUserStoryById(id);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean status = us != null && us.getManager().getUserAccount().getId() == userAccountId || us != null && !us.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		int masterId;
		boolean autorizado;
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		SelectChoices choices = SelectChoices.from(priorityUserStories.class, object.getPriority());

		masterId = super.getRequest().getData("id", int.class);
		UserStory us = this.repo.findUserStoryById(masterId);
		autorizado = us != null && us.getManager().getUserAccount().getId() == userAccountId;

		Dataset dataset;

		dataset = super.unbind(object, "title", "description", "acceptanceCriteria", "estimatedCost", "priority", "link", "draftMode");
		dataset.put("priority", choices);
		dataset.put("autorizado", autorizado);

		super.getResponse().addData(dataset);
	}

}
