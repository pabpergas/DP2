
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
