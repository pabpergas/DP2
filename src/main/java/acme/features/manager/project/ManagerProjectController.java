
package acme.features.manager.project;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.S1.Project;
import acme.roles.Manager;

@Controller
public class ManagerProjectController extends AbstractController<Manager, Project> {

	@Autowired
	private ManagerProjectShowService		showService;

	@Autowired
	private ManagerProjectListMineService	listMineService;

	@Autowired
	private ManagerProjectListAllService	listAllService;

	@Autowired
	private ManagerProjectCreateService		createService;

	@Autowired
	private ManagerProjectDeleteService		deleteService;

	@Autowired
	private ManagerProjectPublishService	publishService;

	@Autowired
	private ManagerProjectUpdateService		updateService;


	@PostConstruct
	protected void initialice() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("publish", "update", this.publishService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("list-all", "list", this.listAllService);
	}
}
