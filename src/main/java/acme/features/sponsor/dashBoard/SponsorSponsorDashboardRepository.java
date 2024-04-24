
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

	//EUR
	@Query("SELECT AVG(s.amount.amount) FROM SponsorShip s WHERE s.amount.currency = 'EUR'")
	Double findAverageAmountEUR();

	@Query("SELECT STDDEV(amount.amount) FROM SponsorShip s WHERE s.amount.currency = 'EUR'")
	Double findDeviationAmountEUR();

	@Query("SELECT MIN(amount.amount) FROM SponsorShip s WHERE s.amount.currency = 'EUR'")
	Double findMinimumAmountEUR();

	@Query("SELECT MAX(amount.amount) FROM SponsorShip s WHERE s.amount.currency = 'EUR'")
	Double findMaximumAmountEUR();

	//USD

	@Query("SELECT AVG(s.amount.amount) FROM SponsorShip s WHERE s.amount.currency = 'USD'")
	Double findAverageAmountUSD();

	@Query("SELECT STDDEV(amount.amount) FROM SponsorShip s WHERE s.amount.currency = 'USD'")
	Double findDeviationAmountUSD();

	@Query("SELECT MIN(amount.amount) FROM SponsorShip s WHERE s.amount.currency = 'USD'")
	Double findMinimumAmountUSD();

	@Query("SELECT MAX(amount.amount) FROM SponsorShip s WHERE s.amount.currency = 'USD'")
	Double findMaximumAmountUSD();

	//GBD
	@Query("SELECT AVG(s.amount.amount) FROM SponsorShip s WHERE s.amount.currency = 'GBD'")
	Double findAverageAmountGBD();

	@Query("SELECT STDDEV(s.amount.amount) FROM SponsorShip s WHERE s.amount.currency = 'GBD'")
	Double findDeviationAmountGBD();

	@Query("SELECT MIN(s.amount.amount) FROM SponsorShip s WHERE s.amount.currency = 'GBD'")
	Double findMinimumAmountGBD();

	@Query("SELECT MAX(s.amount.amount) FROM SponsorShip s WHERE s.amount.currency = 'GBD'")
	Double findMaximumAmountGBD();

	//Invoices

	@Query("SELECT avg(quantity.amount) FROM Invoice i WHERE i.quantity.currency = 'EUR'")
	Double findAverageQuantityEUR();

	@Query("SELECT STDDEV(quantity.amount) FROM Invoice i WHERE i.quantity.currency = 'EUR'")
	Double findDeviationQuantityEUR();

	@Query("SELECT MIN(quantity.amount) FROM Invoice i WHERE i.quantity.currency = 'EUR'")
	Double findMinimumQuantityEUR();

	@Query("SELECT MAX(quantity.amount) FROM Invoice i WHERE i.quantity.currency = 'EUR'")
	Double findMaximumQuantityEUR();

	@Query("SELECT avg(quantity.amount) FROM Invoice i WHERE i.quantity.currency = 'USD'")
	Double findAverageQuantityUSD();

	@Query("SELECT STDDEV(quantity.amount) FROM Invoice i WHERE i.quantity.currency = 'USD'")
	Double findDeviationQuantityUSD();

	@Query("SELECT MIN(quantity.amount) FROM Invoice i WHERE i.quantity.currency = 'USD'")
	Double findMinimumQuantityUSD();

	@Query("SELECT MAX(quantity.amount) FROM Invoice i WHERE i.quantity.currency = 'USD'")
	Double findMaximumQuantityUSD();

	@Query("SELECT avg(quantity.amount) FROM Invoice i WHERE i.quantity.currency = 'GBD'")
	Double findAverageQuantityGBD();

	@Query("SELECT STDDEV(quantity.amount) FROM Invoice i WHERE i.quantity.currency = 'GBD'")
	Double findDeviationQuantityGBD();

	@Query("SELECT MIN(quantity.amount) FROM Invoice i WHERE i.quantity.currency = 'GBD'")
	Double findMinimumQuantityGBD();

	@Query("SELECT MAX(quantity.amount) FROM Invoice i WHERE i.quantity.currency = 'GBD'")
	Double findMaximumQuantityGBD();

}
