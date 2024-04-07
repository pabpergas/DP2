
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S4.Invoice;
import acme.entities.S4.SponsorShip;
import acme.roles.Sponsor;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("select i from Invoice i where i.sponsorShip.sponsor.id = :id")
	Collection<Invoice> findInvoicesBySponsorId(int id);

	@Query("select i from Invoice i where i.id = :id")
	Invoice findOneInvoiceById(int id);

	@Query("select s from Sponsor s where s.id = :id")
	Sponsor findOneSponsorById(int id);

	@Query("select s from SponsorShip s where s.id = :id")
	SponsorShip findOneSponsorShipById(int id);

	@Query("select i from Invoice i where i.code = :code")
	Invoice findOneInvoiceByCode(String code);

	@Query("SELECT DISTINCT s FROM SponsorShip s WHERE s.sponsor.id = :id")
	Collection<SponsorShip> findManySponsorShipsBySponsorId(int id);

}
