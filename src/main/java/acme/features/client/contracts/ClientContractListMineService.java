
package acme.features.client.contracts;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S2.Contract;
import acme.roles.Client;

@Service
public class ClientContractListMineService extends AbstractService<Client, Contract> {

	@Autowired
	private ClientContractRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getPrincipal().getActiveRoleId();
		Collection<Contract> contract;

		contract = this.repository.findManyContractsByClientId(id);
		super.getBuffer().addData(contract);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "project.title", "code", "providerName", "customerName", "goals", "budget", "link");
		super.getResponse().addData(dataset);
	}

}
