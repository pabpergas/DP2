package acme.features.auditor.auditrecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S5.AuditRecord;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {
	
	@Query("SELECT ar FROM AuditRecord ar WHERE ar.codeAudit.id = :id")
	Collection<AuditRecord> findAllByCodeAuditId(int id);
	
	@Query("SELECT ar FROM AuditRecord ar WHERE ar.id = :id")
	AuditRecord findById(int id);
	
	@Query("select a from AuditRecord a where a.codeAudit.auditor.id = :auditorId")
	Collection<AuditRecord> findAllByAuditorId(int auditorId);

}
