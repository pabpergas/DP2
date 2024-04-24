
package acme.features.any.claim;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.groupal.Claim;

@Controller
public class AnyClaimController extends AbstractController<Any, Claim> {

	@Autowired
	private AnyClaimListService		listService;

	@Autowired
	private AnyClaimShowService		showService;

	@Autowired
	private AnyClaimCreateService	createService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);

	}

}
