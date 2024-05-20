
package acme.testing.sponsor.invoices;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.S4.Invoice;
import acme.testing.TestHarness;

public class SponsorInvoiceListMineTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/sponsor/invoice/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int sponsorshipRecordIndex, final int invoiceRecordIndex, final String code, final String sponsorShipCode, final String dueDate) {

		super.signIn("sponsor1", "sponsor1");

		super.clickOnMenu("Sponsor", "SponsorShips");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(sponsorshipRecordIndex);
		super.clickOnButton("Invoices");
		super.checkListingExists();
		super.clickOnListingRecord(invoiceRecordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("SponsorShip Code", sponsorShipCode);
		super.checkInputBoxHasValue("Due Date", dueDate);

		super.signOut();
	}

	@Test
	public void test200Negative() {
	}

	@Test
	public void test300Hacking() {

		Collection<Invoice> invoices;
		String param;

		invoices = this.repository.findManyInvoicesBySponsorUsername("sponsor2");
		for (final Invoice invoice : invoices)
			if (invoice.getSponsorShip().isDraftMode()) {
				param = String.format("id=%d", invoice.getSponsorShip().getId());

				super.checkLinkExists("Sign in");
				super.request("/sponsor/invoice/list-mine", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/sponsor/invoice/list-mine", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("sponsor2", "sponsor2");
				super.request("/sponsor/invoice/list-mine", param);
				super.checkPanicExists();
				super.signOut();

			}
	}

}
