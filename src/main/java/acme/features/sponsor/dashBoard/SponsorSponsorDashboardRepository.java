
package acme.features.sponsor.dashBoard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorSponsorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(i) FROM Invoice i WHERE i.tax <= 21.00 AND i.sponsorShip.sponsor.id = :id")
	int countInvoicesWithTaxLessThanOrEqualTo21Percent(int id);

	@Query("SELECT COUNT(s) FROM SponsorShip s WHERE s.link IS NOT NULL AND s.sponsor.id = :id")
	int countSponsorshipsWithLink(int id);

	@Query("SELECT s.amount.currency, AVG(s.amount.amount) FROM SponsorShip s WHERE s.sponsor.id = :id GROUP BY s.amount.currency")
	Collection<Object[]> averageAmountSponsorshipsBySponsorIdGroupedByCurrency(int id);

	@Query("SELECT s.amount.currency, STDDEV(s.amount.amount) FROM SponsorShip s WHERE s.sponsor.id = :id GROUP BY s.amount.currency")
	Collection<Object[]> deviationAmountSponsorshipsBySponsorIdGroupedByCurrency(int id);

	@Query("SELECT s.amount.currency, MIN(s.amount.amount) FROM SponsorShip s WHERE s.sponsor.id = :id GROUP BY s.amount.currency")
	Collection<Object[]> minimumAmountSponsorshipsBySponsorIdGroupedByCurrency(int id);

	@Query("SELECT s.amount.currency, MAX(s.amount.amount) FROM SponsorShip s WHERE s.sponsor.id = :id GROUP BY s.amount.currency")
	Collection<Object[]> maximumAmountSponsorshipsBySponsorIdGroupedByCurrency(int id);

	//Invoices

	@Query("SELECT i.quantity.currency, AVG(i.quantity.amount) FROM Invoice i WHERE i.sponsorShip.sponsor.id = :id GROUP BY i.quantity.currency")
	Collection<Object[]> averageInvoiceQuantityBySponsorIdGroupedByCurrency(int id);

	@Query("SELECT i.quantity.currency, AVG(i.quantity.amount) FROM Invoice i WHERE i.sponsorShip.sponsor.id = :id GROUP BY i.quantity.currency")
	Collection<Object[]> deviationInvoiceQuantitytBySponsorIdGroupedByCurrency(int id);

	@Query("SELECT i.quantity.currency, AVG(i.quantity.amount) FROM Invoice i WHERE i.sponsorShip.sponsor.id = :id GROUP BY i.quantity.currency")
	Collection<Object[]> minimumInvoiceQuantitysBySponsorIdGroupedByCurrency(int id);

	@Query("SELECT i.quantity.currency, AVG(i.quantity.amount) FROM Invoice i WHERE i.sponsorShip.sponsor.id = :id GROUP BY i.quantity.currency")
	Collection<Object[]> maximumInvoiceQuantityBySponsorIdGroupedByCurrency(int id);
}
