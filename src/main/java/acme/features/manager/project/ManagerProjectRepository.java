
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.S1.Project;
import acme.entities.S1.ProjectUserStories;
import acme.roles.Manager;

public interface ManagerProjectRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT p FROM Project p WHERE p.manager.id = :id")
	Collection<Project> findAllManagerProjectsById(int id);

	@Query("SELECT p FROM Project p")
	Collection<Project> findAllProjects();

	@Query("SELECT m FROM Manager m WHERE m.id = :id")
	Manager findManagerById(int id);

	@Query("SELECT pus FROM ProjectUserStories pus WHERE pus.project.id = :id")
	Collection<ProjectUserStories> findProjectUserStoriesByProjectId(int id);
}
