
package acme.features.sponsor.dashBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorDashboardListService extends AbstractService<Sponsor, SponsorDashboard> {

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

		super.getBuffer().addData(sponsorDashBoard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		assert object != null;

		int totalInvoicesWithTaxLessThanOrEqualTo21Percent = 0;
		int totalSponsorshipsWithLink = 0;
		Double averageSponsorshipAmount;
		Double deviationSponsorshipAmount;
		Double minimumSponsorshipAmount;
		Double maximumSponsorshipAmount;

		Double averageSponsorshipQuantity;
		Double deviationSponsorshipQuantity;
		Double minimumSponsorshipQuantity;
		Double maximumSponsorshipQuantity;

		Dataset dataset;

		totalInvoicesWithTaxLessThanOrEqualTo21Percent = this.repository.countInvoicesWithTaxLessThanOrEqualTo21Percent();
		totalSponsorshipsWithLink = this.repository.countSponsorshipsWithLink();
		averageSponsorshipAmount = this.repository.findAverageAmount();
		deviationSponsorshipAmount = this.repository.findStandardDeviationAmount();
		minimumSponsorshipAmount = this.repository.findMinimumAmount();
		maximumSponsorshipAmount = this.repository.findMaximumAmount();

		averageSponsorshipQuantity = this.repository.findAverageQuantity();
		deviationSponsorshipQuantity = this.repository.findStandardDeviationQuantity();
		minimumSponsorshipQuantity = this.repository.findMinimumQuantity();
		maximumSponsorshipQuantity = this.repository.findMaximumQuantity();

		dataset = super.unbind(object, "totalInvoicesWithTaxLessThanOrEqualTo21Percent");
		dataset.put("totalInvoicesWithTaxLessThanOrEqualTo21Percent", totalInvoicesWithTaxLessThanOrEqualTo21Percent);
		dataset.put("totalSponsorshipsWithLink", totalSponsorshipsWithLink);

		dataset.put("averageSponsorshipAmount", averageSponsorshipAmount);
		dataset.put("deviationSponsorshipAmount", deviationSponsorshipAmount);
		dataset.put("minimumSponsorshipAmount", totalSponsorshipsWithLink);
		dataset.put("maximumSponsorshipAmount", maximumSponsorshipAmount);

		dataset.put("averageSponsorshipQuantity", averageSponsorshipQuantity);
		dataset.put("deviationSponsorshipQuantity", deviationSponsorshipQuantity);
		dataset.put("minimumSponsorshipQuantity", minimumSponsorshipQuantity);
		dataset.put("maximumSponsorshipQuantity", maximumSponsorshipQuantity);

		super.getResponse().addData(dataset);
	}

}
