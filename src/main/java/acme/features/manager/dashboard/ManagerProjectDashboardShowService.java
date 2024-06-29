
package acme.features.manager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.S1.UserStories.priorityUserStories;
import acme.forms.managerDashboard;
import acme.roles.Manager;

@Service
public class ManagerProjectDashboardShowService extends AbstractService<Manager, managerDashboard> {

	@Autowired
	private ManagerProjectDashboardRepository repo;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		managerDashboard db;
		Principal principal = super.getRequest().getPrincipal();
		int id = principal.getAccountId();

		int numProjects = this.repo.numeroDeProyectos(id);
		int numUs = this.repo.numeroDeUs(id);

		db = new managerDashboard();

		Integer totalMusts = this.repo.totalUserStoriesByPriority(priorityUserStories.Must, id);
		Integer totalShoulds = this.repo.totalUserStoriesByPriority(priorityUserStories.Should, id);
		Integer totalCoulds = this.repo.totalUserStoriesByPriority(priorityUserStories.Could, id);
		Integer totalWonts = this.repo.totalUserStoriesByPriority(priorityUserStories.WillNot, id);

		Double avgCostUS = this.repo.averageEstimatedCostUserStories(id);
		Double devCostUS = this.repo.deviationEstimatedCostUserStories(id);
		Double minCostUS = this.repo.minEstimatedCostUserStories(id);
		Double maxCostUS = this.repo.maxEstimatedCostUserStories(id);

		Double avgCostProject = this.repo.averageCostProject(id);
		Double devCostProject = this.repo.deviationCostProject(id);
		Double minCostProject = this.repo.minCostProject(id);
		Double maxCostProject = this.repo.maxCostProject(id);

		db.setTotalMusts(totalMusts);
		db.setTotalShoulds(totalShoulds);
		db.setTotalCoulds(totalCoulds);
		db.setTotalWonts(totalWonts);

		db.setAverageEstimatedCostUserHistory(avgCostUS);
		if (numUs > 1)
			db.setDeviationEstimatedCostUserHistory(devCostUS);
		else
			db.setDeviationEstimatedCostUserHistory(null);
		db.setMinimiunEstimatedCostUserHistory(minCostUS);
		db.setMaximunEstimatedCostUserHistory(maxCostUS);

		db.setAverageEstimatedCostProject(avgCostProject);
		if (numProjects > 1)
			db.setDeviationEstimatedCostProject(devCostProject);
		else
			db.setDeviationEstimatedCostProject(null);
		db.setMinimiunEstimatedCostProject(minCostProject);
		db.setMaximunEstimatedCostProject(maxCostProject);

		super.getBuffer().addData(db);
	}

	@Override
	public void unbind(final managerDashboard object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "totalMusts", "totalShoulds", "totalCoulds", "totalWonts", "averageEstimatedCostUserHistory", "deviationEstimatedCostUserHistory", "minimiunEstimatedCostUserHistory", "maximunEstimatedCostUserHistory",
			"averageEstimatedCostProject", "deviationEstimatedCostProject", "minimiunEstimatedCostProject", "maximunEstimatedCostProject");

		super.getResponse().addData(dataset);
	}

}
