
package acme.testing.sponsor.invoices;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S4.Invoice;
import acme.entities.S4.SponsorShip;

@Repository
public interface SponsorInvoiceTestRepository extends AbstractRepository {

	@Query("select s.invoice from SponsorShip s where s.sponsor.userAccount.username = :username")
	Collection<Invoice> findManyInvoicesBySponsorUsername(String username);

	@Query("select s from SponsorShip s where s.sponsor.userAccount.username = :username")
	Collection<SponsorShip> findManySponsorShipsBySponsorUsername(String username);

}
