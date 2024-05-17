
package acme.testing.sponsor.sponsorShip;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S4.SponsorShip;

@Repository
public interface SponsorSponsorShipTestRepository extends AbstractRepository {

	@Query("select s from SponsorShip s where s.sponsor.userAccount.username = :username")
	Collection<SponsorShip> findManySponsorShipsBySponsorUsername(String username);

}
