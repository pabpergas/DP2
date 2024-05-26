
package acme.features.developer.dashBoard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S3.Sessions;
import acme.entities.S3.Training;
import acme.roles.Developer;

@Repository
public interface DeveloperDeveloperDashboardRespository extends AbstractRepository {

	@Query("select 1.0 * count(tm) from Training tm where tm.updateMoment is not null and tm.developer.userAccount.id = :id")
	double totalTrainingsWithUpdateMoment(@Param("id") int id);

	@Query("select 1.0 * count(ts) from Sessions ts where ts.link is not null and ts.link <> '' and ts.training.developer.userAccount.id = :id")
	double totalSessionsWithLink(@Param("id") int id);

	@Query("select avg(tm.estimatedTotalTime) from Training tm where tm.developer.userAccount.id = :id")
	double averageTrainingsTime(@Param("id") int id);

	@Query("SELECT SQRT((SUM(tm.estimatedTotalTime * tm.estimatedTotalTime) / COUNT(tm.estimatedTotalTime)) - (AVG(tm.estimatedTotalTime) * AVG(tm.estimatedTotalTime))) FROM Training tm where tm.developer.userAccount.id = :id")
	double deviationTrainingsTime(@Param("id") int id);

	@Query("select min(tm.estimatedTotalTime) from Training tm where tm.developer.userAccount.id = :id")
	double minimumTrainingsTime(@Param("id") int id);

	@Query("select max(tm.estimatedTotalTime) from Training tm where tm.developer.userAccount.id = :id")
	double maximumTrainingsTime(@Param("id") int id);

	@Query("select d from Developer d where d.userAccount.id = :id")
	Developer findDeveloperById(@Param("id") int id);

	@Query("select tm from Training tm where tm.developer.userAccount.id = :id")
	Collection<Training> findAllTMByDeveloperId(@Param("id") int id);

	@Query("select ts from Sessions ts where ts.training.developer.userAccount.id = :id")
	Collection<Sessions> findAllTSByDeveloperId(@Param("id") int id);
}
