
package acme.features.authenticated.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S4.SponsorShip;

@Repository
public interface AuthenticatedSponsorShipRepository extends AbstractRepository {

	@Query("select s from SponsorShip s")
	Collection<SponsorShip> findAllSponsorShip();

	@Query("select s from SponsorShip s where s.id = :id")
	SponsorShip findOneSponsorShipById(int id);

}
