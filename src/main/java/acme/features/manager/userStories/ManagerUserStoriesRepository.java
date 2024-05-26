
package acme.features.manager.userStories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.S1.Project;
import acme.entities.S1.ProjectUserStories;
import acme.entities.S1.UserStories;
import acme.roles.Manager;

public interface ManagerUserStoriesRepository extends AbstractRepository {

	@Query("SELECT us FROM UserStories us WHERE us.id = :id")
	UserStories findUserStoryById(int id);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT us FROM UserStories us WHERE us.manager.userAccount.id = :id")
	Collection<UserStories> findUserStoriesByManagerId(int id);

	@Query("SELECT pus FROM ProjectUserStories pus WHERE pus.project.id = :id")
	Collection<ProjectUserStories> findProjectUserStoriesByProjectId(int id);

	@Query("SELECT m FROM Manager m WHERE m.id = :id")
	Manager findManagerById(int id);

	@Query("SELECT us FROM UserStories us")
	Collection<UserStories> findUserStories();

}
