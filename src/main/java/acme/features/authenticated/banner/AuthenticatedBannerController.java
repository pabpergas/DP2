
package acme.features.authenticated.banner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.groupal.Banner;

@Controller
public class AuthenticatedBannerController extends AbstractController<Authenticated, Banner> {

	@Autowired
	private AuthenticatedBannerListService	listService;

	@Autowired
	private AuthenticatedBannerShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);

	}

}
