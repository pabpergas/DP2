
package acme.features.manager.ProjectUserStories;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.S1.ProjectUserStories;
import acme.roles.Manager;

@Controller
public class ManagerProjectUserStoriesController extends AbstractController<Manager, ProjectUserStories> {

	@Autowired
	protected ManagerProjectUserStoriesListService		listService;

	@Autowired
	protected ManagerProjectUserStoriesShowService		showService;

	@Autowired
	protected ManagerProjectUserStoriesCreateService	createService;

	@Autowired
	protected ManagerProjectUserStoriesDeleteService	deleteService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-by-project", "list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
	}
}
