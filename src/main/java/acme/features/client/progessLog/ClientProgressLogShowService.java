
package acme.features.client.progessLog;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S2.Contract;
import acme.roles.Client;

public class ClientProgressLogShowService extends AbstractService<Client, Contract> {

	@Autowired
	private ClientProgressLogRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);

	}

	@Override
	public void load() {
		Contract object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneContractById(id);
		super.getBuffer().addData(object);

	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "project.title", "recordId", "completenessPercentage", "progressComment", "registrationMoment", "responsiblePerson");
		super.getResponse().addData(dataset);

	}
}
