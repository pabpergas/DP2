
package acme.features.sponsor.dashBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	@Autowired
	private SponsorSponsorDashboardRepository repository;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SponsorDashboard sponsorDashBoard;

		sponsorDashBoard = new SponsorDashboard();

		int totalInvoicesWithTaxLessThanOrEqualTo21Percent = 0;
		int totalSponsorshipsWithLink = 0;

		Money averageSponsorshipAmountEUR = new Money();
		Money deviationSponsorshipAmountEUR = new Money();
		Money minimumSponsorshipAmountEUR = new Money();
		Money maximumSponsorshipAmountEUR = new Money();

		Money averageInvoiceQuantityEUR = new Money();
		Money deviationInvoiceQuantityEUR = new Money();
		Money minimumInvoiceQuantityEUR = new Money();
		Money maximumInvoiceQuantityEUR = new Money();

		Money averageSponsorshipAmountUSD = new Money();
		Money deviationSponsorshipAmountUSD = new Money();
		Money minimumSponsorshipAmountUSD = new Money();
		Money maximumSponsorshipAmountUSD = new Money();

		Money averageInvoiceQuantityUSD = new Money();
		Money deviationInvoiceQuantityUSD = new Money();
		Money minimumInvoiceQuantityUSD = new Money();
		Money maximumInvoiceQuantityUSD = new Money();

		totalInvoicesWithTaxLessThanOrEqualTo21Percent = this.repository.countInvoicesWithTaxLessThanOrEqualTo21Percent();
		totalSponsorshipsWithLink = this.repository.countSponsorshipsWithLink();

		//EUR
		averageSponsorshipAmountEUR.setCurrency("EUR");
		averageSponsorshipAmountEUR.setAmount(this.repository.findAverageAmountEUR());
		deviationSponsorshipAmountEUR.setCurrency("EUR");
		deviationSponsorshipAmountEUR.setAmount(this.repository.findDeviationAmountEUR());
		minimumSponsorshipAmountEUR.setCurrency("EUR");
		minimumSponsorshipAmountEUR.setAmount(this.repository.findMinimumAmountEUR());
		maximumSponsorshipAmountEUR.setCurrency("EUR");
		maximumSponsorshipAmountEUR.setAmount(this.repository.findMaximumAmountEUR());

		averageInvoiceQuantityEUR.setCurrency("EUR");
		averageInvoiceQuantityEUR.setAmount(this.repository.findAverageQuantityEUR());
		deviationInvoiceQuantityEUR.setCurrency("EUR");
		deviationInvoiceQuantityEUR.setAmount(this.repository.findDeviationQuantityEUR());
		minimumInvoiceQuantityEUR.setCurrency("EUR");
		minimumInvoiceQuantityEUR.setAmount(this.repository.findMinimumQuantityEUR());
		maximumInvoiceQuantityEUR.setCurrency("EUR");
		maximumInvoiceQuantityEUR.setAmount(this.repository.findMinimumQuantityEUR());

		//USD
		averageSponsorshipAmountUSD.setCurrency("USD");
		averageSponsorshipAmountUSD.setAmount(this.repository.findAverageAmountUSD());
		deviationSponsorshipAmountUSD.setCurrency("USD");
		deviationSponsorshipAmountUSD.setAmount(this.repository.findDeviationAmountUSD());
		minimumSponsorshipAmountUSD.setCurrency("USD");
		minimumSponsorshipAmountUSD.setAmount(this.repository.findMinimumAmountUSD());
		maximumSponsorshipAmountUSD.setCurrency("USD");
		maximumSponsorshipAmountUSD.setAmount(this.repository.findMaximumAmountUSD());

		averageInvoiceQuantityUSD.setCurrency("USD");
		averageInvoiceQuantityUSD.setAmount(this.repository.findAverageQuantityUSD());
		deviationInvoiceQuantityUSD.setCurrency("USD");
		deviationInvoiceQuantityUSD.setAmount(this.repository.findDeviationQuantityUSD());
		minimumInvoiceQuantityUSD.setCurrency("USD");
		minimumInvoiceQuantityUSD.setAmount(this.repository.findMinimumQuantityUSD());
		maximumInvoiceQuantityUSD.setCurrency("USD");
		maximumInvoiceQuantityUSD.setAmount(this.repository.findMaximumQuantityUSD());

		sponsorDashBoard.setTotalInvoicesWithTaxLessThanOrEqualTo21Percent(totalInvoicesWithTaxLessThanOrEqualTo21Percent);
		sponsorDashBoard.setTotalSponsorshipsWithLink(totalSponsorshipsWithLink);
		//-------------------------------------------------------------------------------------------------------------------------
		//EUR
		sponsorDashBoard.setAverageSponsorshipAmountEUR(averageSponsorshipAmountEUR);
		sponsorDashBoard.setDeviationSponsorshipAmountEUR(deviationSponsorshipAmountEUR);
		sponsorDashBoard.setMinimumSponsorshipAmountEUR(minimumSponsorshipAmountEUR);
		sponsorDashBoard.setMaximumSponsorshipAmountEUR(maximumSponsorshipAmountEUR);

		sponsorDashBoard.setAverageInvoiceQuantityEUR(averageInvoiceQuantityEUR);
		sponsorDashBoard.setDeviationInvoiceQuantityEUR(deviationInvoiceQuantityEUR);
		sponsorDashBoard.setMinimumInvoiceQuantityEUR(minimumInvoiceQuantityEUR);
		sponsorDashBoard.setMaximumInvoiceQuantityEUR(maximumInvoiceQuantityEUR);

		//USD
		sponsorDashBoard.setAverageSponsorshipAmountUSD(averageSponsorshipAmountUSD);
		sponsorDashBoard.setDeviationSponsorshipAmountUSD(deviationSponsorshipAmountUSD);
		sponsorDashBoard.setMinimumSponsorshipAmountUSD(minimumSponsorshipAmountUSD);
		sponsorDashBoard.setMaximumSponsorshipAmountUSD(maximumSponsorshipAmountUSD);
		sponsorDashBoard.setAverageInvoiceQuantityUSD(averageInvoiceQuantityUSD);
		sponsorDashBoard.setDeviationInvoiceQuantityUSD(deviationInvoiceQuantityUSD);
		sponsorDashBoard.setMinimumInvoiceQuantityUSD(minimumInvoiceQuantityUSD);
		sponsorDashBoard.setMaximumInvoiceQuantityUSD(maximumInvoiceQuantityUSD);

		super.getBuffer().addData(sponsorDashBoard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "totalInvoicesWithTaxLessThanOrEqualTo21Percent", "totalSponsorshipsWithLink", //
			"averageSponsorshipAmountEUR", "deviationSponsorshipAmountEUR", "minimumSponsorshipAmountEUR", "maximumSponsorshipAmountEUR", "averageInvoiceQuantityEUR", "deviationInvoiceQuantityEUR", "minimumInvoiceQuantityEUR", "maximumInvoiceQuantityEUR",//
			"averageSponsorshipAmountUSD", "deviationSponsorshipAmountUSD", "minimumSponsorshipAmountUSD", "maximumSponsorshipAmountUSD",//
			"averageInvoiceQuantityUSD", "deviationInvoiceQuantityUSD", "minimumInvoiceQuantityUSD", "maximumInvoiceQuantityUSD"//
		);
		super.getResponse().addData(dataset);
	}

}
