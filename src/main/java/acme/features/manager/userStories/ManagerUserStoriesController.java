
package acme.features.manager.userStories;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.S1.UserStory;
import acme.roles.Manager;

@Controller
public class ManagerUserStoriesController extends AbstractController<Manager, UserStory> {

	@Autowired
	private ManagerUserStoriesShowService			showService;

	@Autowired
	private ManagerUserStoriesListService			listService;

	@Autowired
	private ManagerUserStoriesListByProjectService	listByProjectService;

	@Autowired
	private ManagerUserStoriesCreateService			createService;

	@Autowired
	private ManagerUserStoriesDeleteService			deleteService;

	@Autowired
	private ManagerUserStoriesUpdateService			updateService;

	@Autowired
	private ManagerUserStoriesPublishService		publishService;

	@Autowired
	private ManagerUserStoriesListMineService		listMineService;


	@PostConstruct
	protected void initialice() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("list-by-proyect", "list", this.listByProjectService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
