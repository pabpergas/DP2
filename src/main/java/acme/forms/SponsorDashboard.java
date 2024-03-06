
package acme.forms;

import acme.client.data.AbstractForm;

public class SponsorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Integer						totalInvoicesWithTaxLessThanOrEqualTo21Percent;
	Integer						totalSponsorshipsWithLink;
	Double						averageSponsorshipAmount;
	Double						deviationSponsorshipAmount;
	Double						minimumSponsorshipAmount;
	Double						maximumSponsorshipAmount;
	Double						averageInvoiceQuantity;
	Double						deviationInvoiceQuantity;
	Double						minimumInvoiceQuantity;
	Double						maximumInvoiceQuantity;

}
