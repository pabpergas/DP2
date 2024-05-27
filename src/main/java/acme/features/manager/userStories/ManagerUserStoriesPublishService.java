
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
public class ManagerUserStoriesPublishService extends AbstractService<Manager, UserStories> {

	@Autowired
	private ManagerUserStoriesRepository repo;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		UserStories userStory = this.repo.findUserStoryById(id);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = userStory != null && userStory.isDraftMode() && userStory.isDraftMode() && userStory.getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
	}
	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);

		UserStories object = this.repo.findUserStoryById(id);

		super.getBuffer().addData(object);
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

		object.setDraftMode(false);

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
