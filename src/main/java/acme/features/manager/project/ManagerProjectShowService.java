
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S1.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectShowService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository repo;


	@Override
	public void authorise() {
		/*
		 * boolean status;
		 * int pId;
		 * Manager manager;
		 * Project p;
		 * 
		 * pId = super.getRequest().getData("id", int.class);
		 * p = this.repo.findProjectById(pId);
		 * 
		 * manager = p == null ? null : p.getManager();
		 * status = manager != null && super.getRequest().getPrincipal().hasRole(manager);
		 * 
		 * super.getResponse().setAuthorised(status);
		 */

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "summary", "hasFatalErrors", "cost", "link", "draftMode");
		super.getResponse().addData(dataset);
	}
}
