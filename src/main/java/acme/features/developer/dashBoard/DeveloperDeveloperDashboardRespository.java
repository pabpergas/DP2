
package acme.features.developer.dashBoard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DeveloperDeveloperDashboardRespository extends AbstractRepository {

	@Query("SELECT COUNT(t) FROM Training t")
	int totalTraining();

	@Query("SELECT COUNT(s) FROM Sessions s")
	int totalSessions();

	@Query("SELECT AVG(t.estimatedTotalTime) FROM Training t")
	Double averageTimeModule();
}
