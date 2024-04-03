
package acme.entities.S1.userStrories;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.roles.Manager;

@Controller
public class ManagerUserStoriesController extends AbstractController<Manager, UserStories> {

	@Autowired
	private ManagerUserStoriesShowService	showService;

	@Autowired
	private ManagerUserStoriesListService	listService;


	@PostConstruct
	protected void initialice() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
	}
}
