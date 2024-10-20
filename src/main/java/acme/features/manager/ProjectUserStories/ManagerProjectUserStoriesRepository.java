
package acme.features.manager.ProjectUserStories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S1.Project;
import acme.entities.S1.ProjectUserStories;
import acme.entities.S1.UserStory;

@Repository
public interface ManagerProjectUserStoriesRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT us FROM UserStory us WHERE us.id = :id")
	UserStory findUserStoryById(int id);

	@Query("SELECT pus FROM ProjectUserStories pus WHERE pus.id = :id")
	ProjectUserStories findProjectUserStoriesById(int id);

	@Query("SELECT us FROM UserStory us WHERE us.manager.userAccount.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);

	@Query("SELECT pus FROM ProjectUserStories pus WHERE pus.project.id = :id")
	Collection<ProjectUserStories> findProjectUserStoriesByProjectId(int id);

	@Query("SELECT us FROM UserStory us")
	Collection<UserStory> findUserStories();
}
