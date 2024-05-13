
package acme.features.client.contracts;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.S2.Contract;
import acme.roles.Client;

@Controller
public class ClientContractController extends AbstractController<Client, Contract> {

	@Autowired
	private ClientContractListMineService	listMineService;

	@Autowired
	private ClientContractShowService		showService;

	@Autowired
	private ClientContractCreateService		createService;

	@Autowired
	private ClientContractDeleteService		deleteService;

	@Autowired
	private ClientContractPublishService	publishService;

	@Autowired
	private ClientContractUpdateService		updateService;


	@PostConstruct
	protected void initialise() {

		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);

	}

}
