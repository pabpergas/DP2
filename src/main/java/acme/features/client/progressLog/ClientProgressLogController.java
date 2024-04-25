
package acme.features.client.progressLog;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.S2.ProgressLog;
import acme.roles.Client;

@Controller
public class ClientProgressLogController extends AbstractController<Client, ProgressLog> {

	@Autowired
	private ClientProgressLogListMineService	listMineService;

	@Autowired
	private ClientProgressListAllService		listAllService;

	@Autowired
	private ClientProgressLogShowService		showService;

	@Autowired
	private ClientProgressLogCreateService		createService;

	@Autowired
	private ClientProgressLogUpdateService		updateService;


	@PostConstruct
	protected void initialise() {

		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);

		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("list-all", "list", this.listAllService);

	}
}
