
package acme.features.authenticated.invoices;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S4.Invoice;

@Repository
public interface AuthenticatedInvoiceRepository extends AbstractRepository {

	@Query("select i from Invoice i")
	Collection<Invoice> findAllInvoice();

	@Query("select i from Invoice i where i.id = :id")
	Invoice findOneInvoiceById(int id);

}
