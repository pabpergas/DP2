package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S1.Project;
import acme.entities.S5.CodeAudit;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("SELECT ca FROM CodeAudit ca WHERE ca.auditor.id = :id")
	Collection<CodeAudit> findAllByAuditorId(int id);
	
	@Query("SELECT ca FROM CodeAudit ca WHERE ca.id = :id")
	CodeAudit findOneById(int id);
	
	@Query("select a from Auditor a where a.id = :id")
	Auditor findAuditorByAuditorId(int id);
	
	@Query("SELECT p FROM Project p WHERE p.draftMode = false")
	Collection<Project> findAllProjects();
	
	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findOneProject(int id);

	
}
