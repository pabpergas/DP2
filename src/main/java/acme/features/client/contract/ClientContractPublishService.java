
package acme.features.client.contract;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S1.Project;
import acme.entities.S2.Contract;
import acme.entities.S2.ProgressLog;
import acme.roles.Client;

@Service
public class ClientContractPublishService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository repository;

	// AbstractService<Employer, Job> -------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Contract contract;
		Client client;

		masterId = super.getRequest().getData("id", int.class);
		contract = this.repository.findOneContractById(masterId);
		client = contract == null ? null : contract.getClient();
		status = contract != null && !contract.isPublished() && super.getRequest().getPrincipal().hasRole(client);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneContractById(id);
		object.setInstantiationMoment(MomentHelper.getCurrentMoment());

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "providerName", "customerName", "goals", "budget");
		object.setProject(project);
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;
		double totalAmount = 0;

		if (object.getProject() != null) {
			Collection<Contract> listAllContracts = this.repository.findAllContract();
			Collection<Contract> contractsFiltered = listAllContracts.stream().filter(x -> x.getProject().getId() == object.getProject().getId()).toList();
			totalAmount = contractsFiltered.stream().map(x -> x.getBudget().getAmount()).collect(Collectors.summingDouble(x -> x));

		}
		double converterHourToEUR = 24;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;
			existing = this.repository.findOneContractByCode(object.getCode());
			final Contract contract2 = object.getCode().equals("") || object.getCode() == null ? null : this.repository.findOneContractById(object.getId());
			super.state(existing == null || contract2.equals(existing), "code", "client.contract.form.error.duplicated");
		}

	}

	@Override
	public void perform(final Contract object) {
		assert object != null;
		Collection<ProgressLog> progressLogs;

		progressLogs = this.repository.findManyProgressLogByContractId(object.getId());
		progressLogs.stream().forEach(x -> x.setPublished(true));

		object.setPublished(true);
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
