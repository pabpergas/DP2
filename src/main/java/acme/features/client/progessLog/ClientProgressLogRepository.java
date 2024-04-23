
package acme.features.client.progessLog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.S2.Contract;

public interface ClientProgressLogRepository extends AbstractRepository {

	@Query("select s from Contract s")
	Collection<Contract> findAllContract();

	@Query("select c from Contract c where c.client.id = :id")
	Collection<Contract> findManyContractsByClientId(int id);

	@Query("select s from Contract s where s.id = :id")
	Contract findOneContractById(int id);

}
