
package acme.features.manager.userStories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.S1.Project;
import acme.entities.S1.UserStories;

public interface ManagerUserStoriesRepository extends AbstractRepository {

	@Query("SELECT us FROM UserStories us WHERE us.id = :id")
	UserStories findUserStoryById(int id);

	//@Query("SELECT us.project FROM UserStories us WHERE us.id = :id")
	@Query("SELECT p FROM ProjectUserStories pu join pu.project p WHERE pu.userStories.id = :id")
	Project findOneProjectByUserStoryId(int id);

	//@Query("SELECT us FROM UserStories us WHERE us.project.id = :id")
	@Query("SELECT us FROM ProjectUserStories pu join pu.userStories us WHERE pu.project.id = :id")
	Collection<UserStories> findManylUserStoriesByProjectId(int id);

}
