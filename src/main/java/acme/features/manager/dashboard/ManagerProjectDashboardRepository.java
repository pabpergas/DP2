
package acme.features.manager.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S1.UserStory.priorityUserStories;

@Repository
public interface ManagerProjectDashboardRepository extends AbstractRepository {

	@Query("SELECT count(us) FROM UserStory us WHERE us.priority = :p AND us.manager.userAccount.id = :id AND us.draftMode = false")
	Integer totalUserStoriesByPriority(priorityUserStories p, int id);

	@Query("SELECT avg(us.estimatedCost) FROM UserStory us WHERE us.manager.userAccount.id = :id AND us.draftMode = false")
	Double averageEstimatedCostUserStories(int id);

	@Query("SELECT stddev(us.estimatedCost) FROM UserStory us WHERE us.manager.userAccount.id = :id AND us.draftMode = false")
	Double deviationEstimatedCostUserStories(int id);

	@Query("SELECT min(us.estimatedCost) FROM UserStory us WHERE us.manager.userAccount.id = :id AND us.draftMode = false")
	Double minEstimatedCostUserStories(int id);

	@Query("SELECT max(us.estimatedCost) FROM UserStory us WHERE us.manager.userAccount.id = :id AND us.draftMode = false")
	Double maxEstimatedCostUserStories(int id);

	//project

	@Query("SELECT avg(p.cost) FROM Project p WHERE p.manager.userAccount.id = :id AND p.draftMode = false")
	Double averageCostProject(int id);

	@Query("SELECT stddev(p.cost) FROM Project p WHERE p.manager.userAccount.id = :id AND p.draftMode = false")
	Double deviationCostProject(int id);

	@Query("SELECT min(p.cost) FROM Project p WHERE p.manager.userAccount.id = :id AND p.draftMode = false")
	Double minCostProject(int id);

	@Query("SELECT max(p.cost) FROM Project p WHERE p.manager.userAccount.id = :id AND p.draftMode = false")
	Double maxCostProject(int id);

	@Query("SELECT count(p) FROM Project p WHERE p.manager.userAccount.id = :id")
	Integer numeroDeProyectos(int id);

	@Query("SELECT count(us) FROM UserStory us WHERE us.manager.userAccount.id = :id")
	Integer numeroDeUs(int id);
}
