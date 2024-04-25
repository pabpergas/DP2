
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S1.Project;
import acme.entities.S1.ProjectUserStories;
import acme.entities.S1.UserStories;
import acme.roles.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repo;


	@Override
	public void authorise() {
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		int masterId = super.getRequest().getData("id", int.class);
		Project project = this.repo.findProjectById(masterId);

		boolean status = project != null && project.isDraftMode() && project.getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);

		Project object = this.repo.findProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "summary", "hasFatalErrors", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final int proyectId = super.getRequest().getData("id", int.class);
			final boolean duplicatedCode = this.repo.findAllProjects().stream().filter(e -> e.getId() != proyectId).anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!duplicatedCode, "code", "manager.project.form.error.duplicated-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("hasFatalErrors")) {
			final boolean hasErrors = object.isHasFatalErrors();

			super.state(!hasErrors, "hasFatalErrors", "manager.project.form.error.hasErrors");
		}

	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		object.setDraftMode(false);
		Collection<UserStories> ls = this.repo.findProjectUserStoriesByProjectId(object.getId()).stream().map(ProjectUserStories::getUserStories).toList();
		ls.stream().forEach(us -> us.setDraftMode(false));

		this.repo.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "title", "summary", "hasFatalErrors", "cost", "link", "draftMode");

		super.getResponse().addData(dataset);
	}
}
