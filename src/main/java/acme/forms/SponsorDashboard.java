
package acme.forms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	int							totalInvoicesWithTaxLessThanOrEqualTo21Percent;
	int							totalSponsorshipsWithLink;

	List<Money>					averageSponsorshipAmount;
	List<Money>					deviationSponsorshipAmount;
	List<Money>					minimumSponsorshipAmount;
	List<Money>					maximumSponsorshipAmount;

	List<Money>					averageInvoiceQuantity;
	List<Money>					deviationInvoiceQuantity;
	List<Money>					minimumInvoiceQuantity;
	List<Money>					maximumInvoiceQuantity;


	public List<Money> parseoMoney(final Collection<Object[]> stats) {
		List<Money> money = new ArrayList<Money>();

		for (Object[] row : stats) {

			String currency = (String) row[0];
			Double averageAmount = ((Number) row[1]).doubleValue();

			Money m = new Money();
			m.setCurrency(currency);
			m.setAmount(averageAmount);
			money.add(m);
		}
		return money;

	}

}
