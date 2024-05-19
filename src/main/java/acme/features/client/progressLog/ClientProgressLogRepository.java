
package acme.features.client.progressLog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S2.Contract;
import acme.entities.S2.ProgressLog;

@Repository
public interface ClientProgressLogRepository extends AbstractRepository {

	@Query("select i from ProgressLog i where i.contract.id = :id")
	Collection<ProgressLog> findProgressLogsByContractId(int id);

	@Query("select i from ProgressLog i where i.contract.client.id = :id")
	Collection<ProgressLog> findAllProgressLogsByClientId(int id);

	@Query("select i from ProgressLog i where i.id = :id")
	ProgressLog findOneProgressLogById(int id);

	@Query("select i.contract from ProgressLog i where i.id = :id")
	Contract findOneContractByProgressLogId(int id);

	@Query("select s from Contract s where s.id = :id")
	Contract findOneContractById(int id);

	@Query("select pl from ProgressLog pl where pl.recordId = :recordId")
	ProgressLog findOneProgressLogByRecordId(String recordId);

	@Query("select max(pl.completeness) from ProgressLog pl where pl.contract.id = :contractId")
	Double findPublishedProgressLogWithMaxCompletenessPublished(int contractId);
	/*
	 * @Query("select s from Contract s")
	 * Collection<Contract> findAllContract();
	 * 
	 * @Query("select c from Contract c where c.client.id = :id")
	 * Collection<Contract> findManyContractsByClientId(int id);
	 * 
	 * @Query("select s from Contract s where s.id = :id")
	 * Contract findOneContractById(int id);
	 * 
	 * @Query("select i from ProgressLog i where i.contract.id = :id")
	 * Collection<ProgressLog> findProgressLogsByContractId(int id);
	 * 
	 * @Query("select i.contract from ProgressLog i where i.id = :id")
	 * Contract findOneContractByProgressLogId(int id);
	 * 
	 * @Query("select i from ProgressLog i where i.contract.client.id = :id")
	 * Collection<ProgressLog> findAllProgressLogsByClientId(int id);
	 */
}
