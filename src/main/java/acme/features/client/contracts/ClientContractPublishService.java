
package acme.features.client.contracts;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.Project;
import acme.entities.S2.Contract;
import acme.entities.S2.ProgressLog;
import acme.roles.Client;

@Service
public class ClientContractPublishService extends AbstractService<Client, Contract> {

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
		status = contract != null && contract.isDraftMode() && super.getRequest().getPrincipal().hasRole(client);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract object;
		Client client;

		client = this.repository.findOneClientById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Contract();
		object.setClient(client);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);
		super.bind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "link");

		object.setProject(project);

	}

	private Boolean checkContractsBudget(final Contract object) {
		assert object != null;

		if (object.getProject() != null) {
			Collection<Contract> contratos = this.repository.findManyContractByProjectId(object.getProject().getId());

			Double budget = contratos.stream().filter(contract -> !contract.isDraftMode()).mapToDouble(contract -> contract.getBudget().getAmount()).sum();

			Double projectCost = object.getProject().getCost().doubleValue();

			return projectCost >= budget + object.getBudget().getAmount();
		}

		return true;
	}

	@Override
	public void validate(final Contract object) {

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.repository.findOneContractByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "client.contract.form.error.duplicated");

			if (!super.getBuffer().getErrors().hasErrors("budget"))
				super.state(this.checkContractsBudget(object), "budget", "client.contract.form.error.excededBudget");
		}
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;

		Collection<ProgressLog> progressLog;

		object.setDraftMode(false);

		this.repository.save(object);
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

		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "link");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);

	}

}
