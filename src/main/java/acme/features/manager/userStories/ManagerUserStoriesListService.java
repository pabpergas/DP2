
package acme.features.manager.userStories;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S1.UserStories;
import acme.roles.Manager;

@Service
public class ManagerUserStoriesListService extends AbstractService<Manager, UserStories> {

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
		final int managerId = super.getRequest().getPrincipal().getAccountId();
		Collection<UserStories> userStories = this.repo.findUserStories();

		super.getBuffer().addData(userStories);
	}

	@Override
	public void unbind(final UserStories object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "estimatedCost");
		super.getResponse().addData(dataset);
	}
}
