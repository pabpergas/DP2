package acme.features.auditor.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select count(c) from CodeAudit c where c.type = acme.entities.S5.CodeAuditType.STATIC")
	int totalStaticCodeAudits();

	@Query("select count(c) from CodeAudit c where c.type = acme.entities.S5.CodeAuditType.DINAMIC")
	int totalDinamicCodeAudits();

	@Query("select count(a) from AuditRecord a where a.codeAudit.auditor.id = :auditorId group by a.codeAudit.id")
	Collection<Long> findNumberOfAuditRecordByAuditorId(int auditorId);
}
