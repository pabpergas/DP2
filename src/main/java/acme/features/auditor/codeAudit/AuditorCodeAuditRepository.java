package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S5.CodeAudit;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("SELECT ca FROM CodeAudit ca WHERE ca.auditor.id = :id")
	Collection<CodeAudit> findAllByAuditorId(int id);
	
	@Query("SELECT ca FROM CodeAudit ca WHERE ca.id = :id")
	CodeAudit findById(int id);
	
	@Query("select a from Auditor a where a.id = :id")
	Auditor findAuditorByAuditorId(int id);

	
}
