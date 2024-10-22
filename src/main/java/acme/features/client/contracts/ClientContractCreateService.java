
package acme.features.client.contracts;

import java.util.Collection;

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

	@Autowired
	private ClientContractRepository clientContractRepository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		Contract contract = new Contract();
		Client client;

		client = this.clientContractRepository.findOneClientById(super.getRequest().getPrincipal().getActiveRoleId());

		contract.setDraftMode(true);
		contract.setClient(client);
		contract.setInstantiationMoment(MomentHelper.getCurrentMoment());

		super.getBuffer().addData(contract);

	}

	@Override
	public void bind(final Contract object) {

		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		object.setDraftMode(true);
		project = this.clientContractRepository.findOneProjectById(projectId);

		super.bind(object, "code", "providerName", "customerName", "goals", "budget");
		object.setProject(project);
	}

	@Override
	public void validate(final Contract object) {

		//debemos comprobar que no se supera el budget del proyecto que hemos indicado al crearlo, y tb al submitearlo
		//no he tenido en cuenta la restriccion del currency porque el coste del proyecto es en horas, y no monetario

		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing = this.clientContractRepository.findOneContractByCode(object.getCode());
			super.state(existing == null, "code", "client.contract.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			double amount = object.getBudget().getAmount();
			String currency = object.getBudget().getCurrency();

			super.state(amount > 0, "budget", "client.contract.form.error.negative-amount");
			super.state(amount <= 1000000, "budget", "client.contract.error.amount");

			// Validación de la moneda permitida
			boolean validCurrency = currency.equals("EUR") || currency.equals("GBP") || currency.equals("USD");
			super.state(validCurrency, "budget", "client.contract.error.amount.currency");
		}
	}
	//if (object.getProject() != null)
	//	super.state(object.getBudget().getCurrency().equals(object.getProject().getCost()), "budget", "client.contract.form.error.different-currency");

	@Override
	public void perform(final Contract object) {

		assert object != null;
		this.clientContractRepository.save(object);
	}

	@Override
	public void unbind(final Contract object) {

		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;

		projects = this.clientContractRepository.findAllProjects();

		choices = SelectChoices.from(projects, "code", object.getProject());

		Dataset dataset;

		dataset = super.unbind(object, "code", "instantiationMoment", "providerName", "customerName", "goals", "budget");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}
