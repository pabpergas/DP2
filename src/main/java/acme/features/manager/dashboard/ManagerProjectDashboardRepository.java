
package acme.features.manager.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S1.UserStories.priorityUserStories;

@Repository
public interface ManagerProjectDashboardRepository extends AbstractRepository {

	@Query("SELECT count(us) FROM UserStories us WHERE us.priority = :p AND us.manager.userAccount.id = :id")
	Integer totalUserStoriesByPriority(priorityUserStories p, int id);

	@Query("SELECT avg(us.estimatedCost) FROM UserStories us WHERE us.manager.userAccount.id = :id")
	Double averageEstimatedCostUserStories(int id);

	@Query("SELECT stddev(us.estimatedCost) FROM UserStories us WHERE us.manager.userAccount.id = :id")
	Double deviationEstimatedCostUserStories(int id);

	@Query("SELECT min(us.estimatedCost) FROM UserStories us WHERE us.manager.userAccount.id = :id")
	Double minEstimatedCostUserStories(int id);

	@Query("SELECT max(us.estimatedCost) FROM UserStories us WHERE us.manager.userAccount.id = :id")
	Double maxEstimatedCostUserStories(int id);

	//project

	@Query("SELECT avg(p.cost) FROM Project p WHERE p.manager.userAccount.id = :id")
	Double averageCostProject(int id);

	@Query("SELECT stddev(p.cost) FROM Project p WHERE p.manager.userAccount.id = :id")
	Double deviationCostProject(int id);

	@Query("SELECT min(p.cost) FROM Project p WHERE p.manager.userAccount.id = :id")
	Double minCostProject(int id);

	@Query("SELECT max(p.cost) FROM Project p WHERE p.manager.userAccount.id = :id")
	Double maxCostProject(int id);
}
