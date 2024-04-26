
package acme.features.manager.ProjectUserStories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S1.ProjectUserStories;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoriesDeleteService extends AbstractService<Manager, ProjectUserStories> {

	@Autowired
	private ManagerProjectUserStoriesRepository repo;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStories pus = this.repo.findProjectUserStoriesById(id);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = pus != null && pus.getProject().getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStories pus = this.repo.findProjectUserStoriesById(id);

		super.getBuffer().addData(pus);
	}

	@Override
	public void bind(final ProjectUserStories object) {
		assert object != null;

		super.bind(object, "userStories");
	}

	@Override
	public void validate(final ProjectUserStories object) {
		assert object != null;
	}

	@Override
	public void perform(final ProjectUserStories object) {
		assert object != null;

		this.repo.delete(object);
	}

	@Override
	public void unbind(final ProjectUserStories object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "userStories");

		super.getResponse().addData(dataset);
	}
}
