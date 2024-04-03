
package acme.entities.S1.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface ManagerProjectRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT p FROM Project p WHERE p.manager.id = :id")
	Collection<Project> findAllManagerProjectsById(int id);

	@Query("SELECT p FROM Project p where p.draftMode = false")
	Collection<Project> findAllProjectsAvailable();
}
