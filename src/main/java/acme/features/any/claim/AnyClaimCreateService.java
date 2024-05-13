
package acme.features.any.claim;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.groupal.Claim;

@Service
public class AnyClaimCreateService extends AbstractService<Any, Claim> {

	@Autowired
	private AnyClaimRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Claim object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		object = new Claim();
		object.setInstantiation(moment);
		super.getBuffer().addData(object);

	}

	@Override
	public void bind(final Claim object) {
		assert object != null;

		super.bind(object, "code", "heading", "description", "department", "email", "link");
	}

	@Override
	public void validate(final Claim object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Claim existing;

			existing = this.repository.findOneClaimByCode(object.getCode());
			super.state(existing == null, "code", "claim.error.duplicated");
		}

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void perform(final Claim object) {
		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setInstantiation(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "heading", "description", "instantiation", "department");
		dataset.put("confirmation", false);
		dataset.put("readonly", false);
		super.getResponse().addData(dataset);

	}

}
