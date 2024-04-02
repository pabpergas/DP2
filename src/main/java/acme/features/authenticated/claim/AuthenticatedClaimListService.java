
package acme.features.authenticated.claim;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.groupal.Claim;

@Service
public class AuthenticatedClaimListService extends AbstractService<Authenticated, Claim> {

	@Autowired
	private AuthenticatedClaimRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Claim> sponsorShip;

		sponsorShip = this.repository.findAllClaims();
		super.getBuffer().addData(sponsorShip);
	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "instantiation", "heading", "description", "department", "email", "link");
		super.getResponse().addData(dataset);
	}

}
