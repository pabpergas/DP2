
package acme.features.sponsor.sponsorShip;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.S1.Project;
import acme.entities.S4.SponsorShip;
import acme.roles.Sponsor;

@Repository
public interface SponsorSponsorShipRepository extends AbstractRepository {

	@Query("select s from SponsorShip s where s.sponsor.id = :id")
	Collection<SponsorShip> findSponsorShipBySponsorId(int id);

	@Query("select s from SponsorShip s where s.id = :id")
	SponsorShip findOneSponsorShipById(int id);

	@Query("select s from Sponsor s where s.id = :id")
	Sponsor findOneSponsorById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("SELECT DISTINCT s.project FROM SponsorShip s WHERE s.sponsor.id = :id")
	Collection<Project> findManyProjectsBySponsorId(int id);

	@Query("select s from SponsorShip s where s.code = :code")
	SponsorShip findOneSponsorShipByCode(String code);

	@Query("select s from SponsorShip s where s.code = :code AND s.id <> :id ")
	SponsorShip findOneSponsorShipByCodeAndDistinctId(String code, int id);

}
