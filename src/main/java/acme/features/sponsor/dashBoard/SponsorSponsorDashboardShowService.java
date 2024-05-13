
package acme.features.sponsor.dashBoard;

import java.util.Collection;

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
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SponsorDashboard sponsorDashBoard;
		int sponsorId;
		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		sponsorDashBoard = new SponsorDashboard();

		int totalInvoicesWithTaxLessThanOrEqualTo21Percent = 0;
		int totalSponsorshipsWithLink = 0;

		Collection<Object[]> averageSponsorshipAmount;
		Collection<Object[]> deviationSponsorshipAmount;
		Collection<Object[]> minimumSponsorshipAmount;
		Collection<Object[]> maximumSponsorshipAmount;

		Collection<Object[]> averageInvoiceQuantity;
		Collection<Object[]> deviationInvoiceQuantity;
		Collection<Object[]> minimumInvoiceQuantity;
		Collection<Object[]> maximumInvoiceQuantity;

		totalInvoicesWithTaxLessThanOrEqualTo21Percent = this.repository.countInvoicesWithTaxLessThanOrEqualTo21Percent(sponsorId);
		totalSponsorshipsWithLink = this.repository.countSponsorshipsWithLink(sponsorId);

		averageSponsorshipAmount = this.repository.averageAmountSponsorshipsBySponsorIdGroupedByCurrency(sponsorId);
		deviationSponsorshipAmount = this.repository.deviationAmountSponsorshipsBySponsorIdGroupedByCurrency(sponsorId);
		minimumSponsorshipAmount = this.repository.minimumAmountSponsorshipsBySponsorIdGroupedByCurrency(sponsorId);
		maximumSponsorshipAmount = this.repository.maximumAmountSponsorshipsBySponsorIdGroupedByCurrency(sponsorId);

		averageInvoiceQuantity = this.repository.averageInvoiceQuantityBySponsorIdGroupedByCurrency(sponsorId);
		deviationInvoiceQuantity = this.repository.deviationInvoiceQuantitytBySponsorIdGroupedByCurrency(sponsorId);
		minimumInvoiceQuantity = this.repository.minimumInvoiceQuantitysBySponsorIdGroupedByCurrency(sponsorId);
		maximumInvoiceQuantity = this.repository.maximumInvoiceQuantityBySponsorIdGroupedByCurrency(sponsorId);

		sponsorDashBoard.setTotalInvoicesWithTaxLessThanOrEqualTo21Percent(totalInvoicesWithTaxLessThanOrEqualTo21Percent);
		sponsorDashBoard.setTotalSponsorshipsWithLink(totalSponsorshipsWithLink);

		sponsorDashBoard.setAverageSponsorshipAmount(sponsorDashBoard.parseoMoney(averageSponsorshipAmount));
		sponsorDashBoard.setDeviationSponsorshipAmount(sponsorDashBoard.parseoMoney(deviationSponsorshipAmount));
		sponsorDashBoard.setMinimumSponsorshipAmount(sponsorDashBoard.parseoMoney(minimumSponsorshipAmount));
		sponsorDashBoard.setMaximumSponsorshipAmount(sponsorDashBoard.parseoMoney(maximumSponsorshipAmount));

		//Invoices
		sponsorDashBoard.setAverageInvoiceQuantity(sponsorDashBoard.parseoMoney(averageInvoiceQuantity));
		sponsorDashBoard.setDeviationInvoiceQuantity(sponsorDashBoard.parseoMoney(deviationInvoiceQuantity));
		sponsorDashBoard.setMinimumInvoiceQuantity(sponsorDashBoard.parseoMoney(minimumInvoiceQuantity));
		sponsorDashBoard.setMaximumInvoiceQuantity(sponsorDashBoard.parseoMoney(maximumInvoiceQuantity));

		//-------------------------------------------------------------------------------------------------------------------------

		super.getBuffer().addData(sponsorDashBoard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "totalInvoicesWithTaxLessThanOrEqualTo21Percent", "totalSponsorshipsWithLink", //
			"averageSponsorshipAmount", "deviationSponsorshipAmount", "minimumSponsorshipAmount", "maximumSponsorshipAmount",//
			"averageInvoiceQuantity", "deviationInvoiceQuantity", "minimumInvoiceQuantity", "maximumInvoiceQuantity"//
		);
		super.getResponse().addData(dataset);
	}

}
