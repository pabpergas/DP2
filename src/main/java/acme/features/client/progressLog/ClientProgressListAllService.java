
package acme.features.client.progressLog;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S2.ProgressLog;
import acme.roles.Client;

@Service
public class ClientProgressListAllService extends AbstractService<Client, ProgressLog> {

	@Autowired
	private ClientProgressLogRepository repository;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Client.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<ProgressLog> progressLogs;
		int clientId;

		clientId = super.getRequest().getPrincipal().getActiveRoleId();

		progressLogs = this.repository.findAllProgressLogsByClientId(clientId);

		super.getBuffer().addData(progressLogs);
	}

	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;

		int masterId;
		Dataset dataset;

		dataset = super.unbind(object, "recordId", "completenessPercentage", "progressComment", "registrationMoment", "responsiblePerson");
		super.getResponse().addData(dataset);
	}

}
