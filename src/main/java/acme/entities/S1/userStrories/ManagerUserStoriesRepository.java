
package acme.entities.S1.userStrories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.S1.project.Project;

public interface ManagerUserStoriesRepository extends AbstractRepository {

	@Query("SELECT us FROM UserStories us WHERE us.id = :id")
	UserStories findUserStoryById(int id);

	@Query("SELECT us.project FROM UserStories us WHERE us.id = :id")
	Project findOneProjectByUserStoryId(int id);

	@Query("SELECT us FROM UserStories us WHERE us.project.id = :id")
	Collection<UserStories> findManylUserStoriesById(int id);

}
