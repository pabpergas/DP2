
package acme.features.sponsor.dashBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		SponsorDashboard sponsorDashBoard;

		sponsorDashBoard = new SponsorDashboard();

		int totalInvoicesWithTaxLessThanOrEqualTo21Percent = 0;
		int totalSponsorshipsWithLink = 0;
		Double averageSponsorshipAmount;
		Double deviationSponsorshipAmount;
		Double minimumSponsorshipAmount;
		Double maximumSponsorshipAmount;

		Double averageInvoiceQuantity;
		Double deviationInvoiceQuantity;
		Double minimumInvoiceQuantity;
		Double maximumInvoiceQuantity;

		Dataset dataset;

		totalInvoicesWithTaxLessThanOrEqualTo21Percent = this.repository.countInvoicesWithTaxLessThanOrEqualTo21Percent();
		totalSponsorshipsWithLink = this.repository.countSponsorshipsWithLink();
		averageSponsorshipAmount = this.repository.findAverageAmount();
		deviationSponsorshipAmount = this.repository.findStandardDeviationAmount();
		minimumSponsorshipAmount = this.repository.findMinimumAmount();
		maximumSponsorshipAmount = this.repository.findMaximumAmount();

		averageInvoiceQuantity = this.repository.findAverageQuantity();
		deviationInvoiceQuantity = this.repository.findStandardDeviationQuantity();
		minimumInvoiceQuantity = this.repository.findMinimumQuantity();
		maximumInvoiceQuantity = this.repository.findMaximumQuantity();

		sponsorDashBoard.setTotalInvoicesWithTaxLessThanOrEqualTo21Percent(totalInvoicesWithTaxLessThanOrEqualTo21Percent);
		sponsorDashBoard.setTotalSponsorshipsWithLink(totalSponsorshipsWithLink);

		sponsorDashBoard.setAverageSponsorshipAmount(averageSponsorshipAmount);
		sponsorDashBoard.setDeviationSponsorshipAmount(deviationSponsorshipAmount);
		sponsorDashBoard.setTotalSponsorshipsWithLink(totalSponsorshipsWithLink);
		sponsorDashBoard.setMaximumSponsorshipAmount(maximumSponsorshipAmount);

		sponsorDashBoard.setAverageInvoiceQuantity(averageInvoiceQuantity);
		sponsorDashBoard.setDeviationInvoiceQuantity(deviationInvoiceQuantity);
		sponsorDashBoard.setMinimumInvoiceQuantity(minimumSponsorshipAmount);
		sponsorDashBoard.setMaximumInvoiceQuantity(maximumSponsorshipAmount);

		super.getBuffer().addData(sponsorDashBoard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		assert object != null;

		super.getResponse().addData(dataset);
	}

}
