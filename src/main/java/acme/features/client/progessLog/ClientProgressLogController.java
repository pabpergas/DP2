
package acme.features.client.progessLog;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.controllers.AbstractController;
import acme.entities.S2.Contract;
import acme.roles.Client;

public class ClientProgressLogController extends AbstractController<Client, Contract> {

	@Autowired
	private ClientProgessLogListMineService	listMineService;

	@Autowired
	private ClientProgressLogShowService	showService;


	@PostConstruct
	protected void initialise() {

		super.addBasicCommand("show", this.showService);
		super.addCustomCommand("list-mine", "list", this.listMineService);

	}
}
