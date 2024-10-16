
package acme.features.manager.userStories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.S1.Project;
import acme.entities.S1.ProjectUserStories;
import acme.entities.S1.UserStory;
import acme.roles.Manager;

public interface ManagerUserStoriesRepository extends AbstractRepository {

	@Query("SELECT us FROM UserStory us WHERE us.id = :id")
	UserStory findUserStoryById(int id);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT us FROM UserStory us WHERE us.manager.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);

	@Query("SELECT pus FROM ProjectUserStories pus WHERE pus.project.id = :id")
	Collection<ProjectUserStories> findProjectUserStoriesByProjectId(int id);

	@Query("SELECT m FROM Manager m WHERE m.id = :id")
	Manager findManagerById(int id);

	@Query("SELECT us FROM UserStory us")
	Collection<UserStory> findUserStories();

}
