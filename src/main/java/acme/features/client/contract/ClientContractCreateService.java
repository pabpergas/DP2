
package acme.features.client.contract;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.Project;
import acme.entities.S2.Contract;
import acme.roles.Client;

@Service
public class ClientContractCreateService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Contract object;
		Client client;
		List<Project> projects = this.repository.findAllProjectsPublished().stream().toList();

		client = this.repository.findOneClientById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Contract();
		object.setPublished(false);
		object.setInstantiationMoment(MomentHelper.getCurrentMoment());
		object.setClient(client);
		object.setProject(projects.get(0));

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "providerName", "customerName", "goals", "budget", "project");
		object.setProject(project);
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.repository.findOneContractByCode(object.getCode());
			super.state(existing == null, "code", "client.contract.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			super.state(object.getBudget().getAmount() <= 1000000.00, "budget", "client.contract.form.error.higher-amount");
			super.state(object.getBudget().getAmount() >= 0.00, "budget", "client.contract.form.error.lower-amount");
			super.state(object.getBudget().getCurrency().equals("EUR"), "budget", "client.contract.form.error.currency");
		}
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		projects = this.repository.findAllProjectsPublished();

		choices = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget", "project", "client", "published");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}
