
package acme.features.authenticated.contracts;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.S2.Contract;

@Controller
public class AuthenticatedContractController extends AbstractController<Authenticated, Contract> {

	@Autowired
	private AuthenticatedContractListService	listService;

	@Autowired
	private AuthenticatedContractShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);

	}

}
