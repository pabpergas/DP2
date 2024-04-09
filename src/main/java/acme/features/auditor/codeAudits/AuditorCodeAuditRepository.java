package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S5.CodeAudit;
import acme.entities.S1.Project;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository{
	
	@Query("SELET ca FROM CodeAudit ca WHERE ca.auditor.id = :id")
	Collection<CodeAudit> findCodeAuditByAuditorId(int id);
	
	@Query("SELECT ca FROM CodeAudit ca WHERE ca.id = :id")
	CodeAudit findCodeAuditById(int id);
	
	@Query("SELECT DISTINCT ca.prject FROM CodeAudit ca WHERE ca.auditor.id = :id")
	Collection<Project> findProjectsByAuditorId(int id);
	
	@Query("SELECT a FROM Auditor WHERE a.id = :id")
	Auditor findAuditorById(int id);

	@Query("SELECT ca.project FROM CodeAudit ca WHERE ca.id == :id")
	Project findProjectByCodeAuditId(int id);

}
