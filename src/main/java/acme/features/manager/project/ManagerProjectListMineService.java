
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S1.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectListMineService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repo;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		final Principal principal = super.getRequest().getPrincipal();

		final boolean status = principal.hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Project> projects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		projects = this.repo.findAllManagerProjectsById(principal.getActiveRoleId());

		super.getBuffer().addData(projects);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "cost");
		super.getResponse().addData(dataset);
	}
}
