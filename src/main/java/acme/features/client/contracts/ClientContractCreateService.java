
package acme.features.client.contracts;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.S2.Contract;
import acme.roles.Client;

public class ClientContractCreateService extends AbstractService<Client, Contract> {

	@Autowired
	private ClientContractRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Contract object;
		int contractId;

		contractId = super.getRequest().getPrincipal().getActiveRoleId(); // Obtener el ID del contrato
		object = this.repository.findOneContractById(contractId); // Buscar el contrato por su ID
		object.setDraftMode(true);
		object.setMoment(MomentHelper.getCurrentMoment());
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		int contractId;
		Contract contract;

		contractId = super.getRequest().getData("project", int.class);
		contract = this.repository.findOneContractById(contractId);
		super.bind(object, "project.title", "code", "providerName", "customerName", "goals", "budget", "link");

		object.setContract(contract);
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;
			existing = this.repository.findOneContractById(object.getId());
			super.state(existing == null, "code", "client.contract.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget"))
			super.state(object.getBudget().getAmount() > 0 && object.getBudget().getAmount() <= 1000000, "budget", "client.contract.error.budget");
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		int contractId;
		Collection<Contract> contract;
		SelectChoices choices;
		SelectChoices typeChoices;
		Dataset dataset;

		contractId = super.getRequest().getPrincipal().getActiveRoleId();
		contract = this.repository.findManyContractsByClientId(contractId);

		choices = SelectChoices.from(contract, "title", object.getContract());
		typeChoices = SelectChoices.from(ContractType.class, object.getType());

		dataset = super.unbind(object, "project.title", "code", "providerName", "customerName", "goals", "budget", "link");

		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		dataset.put("type", typeChoices.getSelected().getKey());
		dataset.put("types", typeChoices);

		super.getResponse().addData(dataset);

	}

}
