
package acme.features.client.contracts;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.Project;
import acme.entities.S2.Contract;
import acme.roles.Client;

@Service
public class ClientContractShowService extends AbstractService<Client, Contract> {

	@Autowired
	private ClientContractRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int masterId;
		Contract contract;
		Client client;

		masterId = super.getRequest().getData("id", int.class);
		contract = this.repository.findOneContractById(masterId);
		client = contract == null ? null : contract.getClient();
		status = contract != null && (!contract.isDraftMode() || super.getRequest().getPrincipal().hasRole(client));
		super.getResponse().setAuthorised(status);

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

		int clientId;
		SelectChoices choices;

		Collection<Project> projects;

		Dataset dataset;

		clientId = super.getRequest().getPrincipal().getActiveRoleId();
		projects = this.repository.findManyProjectsByClientId(clientId);

		choices = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "link", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);

	}
}
