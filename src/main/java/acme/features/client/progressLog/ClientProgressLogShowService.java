
package acme.features.client.progressLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S2.Contract;
import acme.entities.S2.ProgressLog;
import acme.roles.Client;

@Service
public class ClientProgressLogShowService extends AbstractService<Client, ProgressLog> {

	@Autowired
	private ClientProgressLogRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int invoiceId;
		Contract contract;
		invoiceId = super.getRequest().getData("id", int.class);
		contract = this.repository.findOneContractByProgressLogId(invoiceId);
		status = contract != null && (!contract.isDraftMode() || super.getRequest().getPrincipal().hasRole(contract.getClient()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProgressLog object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProgressLogById(id);
		super.getBuffer().addData(object);

	}

	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "recordId", "completenessPercentage", "progressComment", "registrationMoment", "responsiblePerson", "draftMode");
		dataset.put("masterId", object.getContract().getId());
		super.getResponse().addData(dataset);

	}
}
