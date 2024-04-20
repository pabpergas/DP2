
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S4.Invoice;
import acme.entities.S4.SponsorShip;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("select i from Invoice i where i.sponsorShip.id = :id")
	Collection<Invoice> findInvoicesBySponsorShipId(int id);

	@Query("select i from Invoice i where i.id = :id")
	Invoice findOneInvoiceById(int id);

	@Query("select i.sponsorShip from Invoice i where i.id = :id")
	SponsorShip findOneSponsorShipByInvoiceId(int id);

	@Query("select s from SponsorShip s where s.id = :id")
	SponsorShip findOneSponsorShipById(int id);

	@Query("select i from Invoice i where i.code = :code")
	Invoice findOneInvoiceByCode(String code);

	@Query("select i from Invoice i where i.code = :code AND i.id <> :id ")
	Invoice findOneInvoiceByCodeAndDistinctId(String code, int id);

}
