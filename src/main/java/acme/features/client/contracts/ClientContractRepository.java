
package acme.features.client.contracts;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S1.Project;
import acme.entities.S2.Contract;
import acme.entities.S2.ProgressLog;
import acme.roles.Client;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select s from Contract s")
	Collection<Contract> findAllContract();

	@Query("select c from Contract c where c.client.id = :id")
	Collection<Contract> findManyContractsByClientId(int id);

	@Query("select s from Contract s where s.id = :id")
	Contract findOneContractById(int id);

	@Query("select c from Client c where c.id = :id")
	Client findOneClientById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("SELECT DISTINCT c.project FROM Contract c WHERE c.client.id = :id")
	Collection<Project> findManyProjectsByClientId(int id);

	@Query("select i from ProgressLog i where i.contract.id = :id")
	Collection<ProgressLog> findManyProgressLogByContractId(int id);

	@Query("select c from Contract c where c.code = :code")
	Contract findOneContractByCode(String code);

	@Query("select c from Contract c where c.project.id = :id")
	Collection<Contract> findManyContractByProjectId(int id);
}
