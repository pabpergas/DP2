
package acme.features.developer.trainingSessions;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S3.Sessions;
import acme.entities.S3.Training;

@Repository
public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select tm from Training tm where tm.id = :tmId")
	Training findOneTMById(int tmId);

	@Query("select ts from Sessions ts where ts.code = :tsCode")
	Sessions findOneTSByCode(String tsCode);

	@Query("select ts.training from Sessions ts where ts.id = :tsId")
	Training findOneTMByTSId(int tsId);

	@Query("select ts from Sessions ts where ts.id = :tsId")
	Sessions findOneTSById(int tsId);

	@Query("select ts from Sessions ts where ts.training.id = :tmId")
	Collection<Sessions> findAllTSByMasterId(@Param("tmId") int tmId);

}
