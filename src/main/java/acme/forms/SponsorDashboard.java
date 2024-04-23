
package acme.forms;

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

	//EUROS
	Money						averageSponsorshipAmountEUR;
	Money						deviationSponsorshipAmountEUR;
	Money						minimumSponsorshipAmountEUR;
	Money						maximumSponsorshipAmountEUR;

	Money						averageSponsorshipAmountUSD;
	Money						deviationSponsorshipAmountUSD;
	Money						minimumSponsorshipAmountUSD;
	Money						maximumSponsorshipAmountUSD;

	//Invoices
	Money						averageInvoiceQuantityEUR;
	Money						deviationInvoiceQuantityEUR;
	Money						minimumInvoiceQuantityEUR;
	Money						maximumInvoiceQuantityEUR;

	Money						averageInvoiceQuantityUSD;
	Money						deviationInvoiceQuantityUSD;
	Money						minimumInvoiceQuantityUSD;
	Money						maximumInvoiceQuantityUSD;

}
