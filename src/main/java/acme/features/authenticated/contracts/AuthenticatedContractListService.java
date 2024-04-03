
package acme.features.authenticated.contracts;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S2.Contract;

@Service
public class AuthenticatedContractListService extends AbstractService<Authenticated, Contract> {

	@Autowired
	private AuthenticatedContractRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Contract> contract;

		contract = this.repository.findAllContract();
		super.getBuffer().addData(contract);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "project.title", "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "link");
		super.getResponse().addData(dataset);
	}

}
