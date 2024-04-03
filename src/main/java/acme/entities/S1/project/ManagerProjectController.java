
package acme.entities.S1.project;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.roles.Manager;

@Controller
public class ManagerProjectController extends AbstractController<Manager, Project> {

	@Autowired
	private ManagerProjectShowService		showService;

	@Autowired
	private ManagerProjectListMineService	listMineService;

	@Autowired
	private ManagerProjectListAllService	listAllService;


	@PostConstruct
	protected void initialice() {
		super.addBasicCommand("show", this.showService);

		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("list-all", "list", this.listAllService);
	}
}
