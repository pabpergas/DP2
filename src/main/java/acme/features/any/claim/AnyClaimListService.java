
package acme.features.any.claim;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.groupal.Claim;

@Service
public class AnyClaimListService extends AbstractService<Any, Claim> {

	@Autowired
	private AnyClaimRepository repository;


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

		dataset = super.unbind(object, "code", "instantiation", "department");
		super.getResponse().addData(dataset);
	}

}
