
package acme.features.sponsor.dashBoard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorSponsorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(i) FROM Invoice i WHERE i.tax <= 21.00")
	int countInvoicesWithTaxLessThanOrEqualTo21Percent();

	@Query("SELECT COUNT(s) FROM SponsorShip s WHERE s.link IS NOT NULL")
	int countSponsorshipsWithLink();

	@Query("SELECT avg(amount.amount) FROM SponsorShip")
	Double findAverageAmount();

	@Query("SELECT STDDEV(amount.amount) FROM SponsorShip")
	Double findStandardDeviationAmount();

	@Query("SELECT MIN(amount.amount) FROM SponsorShip")
	Double findMinimumAmount();

	@Query("SELECT MAX(amount.amount) FROM SponsorShip")
	Double findMaximumAmount();

	@Query("SELECT avg(quantity.amount) FROM Invoice")
	Double findAverageQuantity();

	@Query("SELECT STDDEV(quantity.amount) FROM Invoice")
	Double findStandardDeviationQuantity();

	@Query("SELECT MIN(quantity.amount) FROM Invoice")
	Double findMinimumQuantity();

	@Query("SELECT MAX(quantity.amount) FROM Invoice")
	Double findMaximumQuantity();

}
