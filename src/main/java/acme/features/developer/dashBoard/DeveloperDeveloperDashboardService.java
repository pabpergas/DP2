
package acme.features.developer.dashBoard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S3.Sessions;
import acme.entities.S3.Training;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDeveloperDashboardService extends AbstractService<Developer, DeveloperDashboard> {

	@Autowired
	private DeveloperDeveloperDashboardRespository repository;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Developer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();
		DeveloperDashboard developerDashboard = new DeveloperDashboard();
		Collection<Training> modules = this.repository.findAllTMByDeveloperId(userAccountId);
		Collection<Sessions> sessions = this.repository.findAllTSByDeveloperId(userAccountId);

		developerDashboard.setTotalTrainingSessions(0.);
		developerDashboard.setTotalTrainingModules(0.);
		developerDashboard.setAverageTimeModule(Double.NaN);
		developerDashboard.setDeviationTimeModule(Double.NaN);
		developerDashboard.setMinimumTimeModule(Double.NaN);
		developerDashboard.setMaximumTimeModule(Double.NaN);

		if (!modules.isEmpty()) {
			developerDashboard.setTotalTrainingModules(this.repository.totalTrainingsWithUpdateMoment(userAccountId));
			developerDashboard.setTotalTrainingSessions(this.repository.totalSessionsWithLink(userAccountId));
			developerDashboard.setLastUpdateMoment(this.repository.totalTrainingsWithUpdateMoment(userAccountId));
			developerDashboard.setAverageTimeModule(this.repository.averageTrainingsTime(userAccountId));
			developerDashboard.setDeviationTimeModule(this.repository.deviationTrainingsTime(userAccountId));
			developerDashboard.setMinimumTimeModule(this.repository.minimumTrainingsTime(userAccountId));
			developerDashboard.setMaximumTimeModule(this.repository.maximumTrainingsTime(userAccountId));
		}

		if (!sessions.isEmpty())
			developerDashboard.setTrainingSessionsLink(this.repository.totalSessionsWithLink(userAccountId));

		super.getBuffer().addData(developerDashboard);

	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "totalTrainingModules", "totalTrainingSessions", "averageTimeModule", "lastUpdateMoment", "trainingSessionsLink", "deviationTimeModule", "minimumTimeModule", "maximumTimeModule");
		super.getResponse().addData(dataset);
	}
}
