
package acme.features.developer.dashBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
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
		DeveloperDashboard developerDashBoard;
		developerDashBoard = new DeveloperDashboard();

		developerDashBoard.setTotalTrainingModules(this.repository.totalTraining());
		developerDashBoard.setTotalTrainingSessions(this.repository.totalSessions());
		developerDashBoard.setAverageTimeModule(this.repository.averageTimeModule());

		super.getBuffer().addData(developerDashBoard);
	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "totalTrainingModules", "totalTrainingSessions", "averageTimeModule");
		super.getResponse().addData(dataset);
	}
}
