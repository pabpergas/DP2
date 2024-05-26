
package acme.features.developer.trainingModules;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S1.Project;
import acme.entities.S3.Sessions;
import acme.entities.S3.Training;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("select t from Training t where t.developer.id = :developerId")
	List<Training> findAllTMByDevId(int developerId);

	@Query("select t.details from Training t where t.developer.id = :developerId")
	List<String> findAllTMDetailsByDevId(int developerId);

	@Query("select t from Training t where t.id = :tmId")
	Training findOneTMById(int tmId);

	@Query("select d from Developer d where d.id = :developerId")
	Developer findOneDeveloperById(int developerId);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select t from Training t where t.code = :tmCode")
	Training findOneTMByCode(String tmCode);

	@Query("select ts from Sessions ts where ts.training.id = :tmId")
	Collection<Sessions> findAllTSByTMId(int tmId);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

}
