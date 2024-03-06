
package acme.forms;

import acme.client.data.AbstractForm;

public class SponsorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	int							totalInvoicesWithTaxLessThanOrEqualTo21Percent;
	int							totalSponsorshipsWithLink;
	Double						averageSponsorshipAmount;
	Double						deviationSponsorshipAmount;
	Double						minimumSponsorshipAmount;
	Double						maximumSponsorshipAmount;
	Double						averageInvoiceQuantity;
	Double						deviationInvoiceQuantity;
	Double						minimumInvoiceQuantity;
	Double						maximumInvoiceQuantity;

}
