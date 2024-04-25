
package acme.features.authenticated.contracts;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S2.Contract;

@Repository
public interface AuthenticatedContractRepository extends AbstractRepository {

	@Query("select s from Contract s")
	Collection<Contract> findAllContract();

	@Query("select s from Contract s where s.id = :id")
	Contract findOneContractById(int id);
}
