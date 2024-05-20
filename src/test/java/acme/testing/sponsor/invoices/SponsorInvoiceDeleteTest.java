
package acme.testing.sponsor.invoices;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.S4.SponsorShip;
import acme.testing.TestHarness;

public class SponsorInvoiceDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/sponsor/invoice/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int sponsorshipRecordIndex, final int invoiceRecordIndex, final String code, final String sponsorShipCode, final String registrationTime, final String dueDate, final String quantity, final String tax,
		final String link) {

		super.signIn("sponsor1", "sponsor1");

		super.clickOnMenu("Sponsor", "SponsorShips");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(sponsorshipRecordIndex);
		super.clickOnButton("Invoices");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(invoiceRecordIndex);

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("Due Date", dueDate);
		super.fillInputBoxIn("Quantity", quantity);
		super.fillInputBoxIn("Tax", tax);
		super.fillInputBoxIn("Link", link);
		super.clickOnSubmit("Delete");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("SponsorShip Code", sponsorShipCode);
		super.checkInputBoxHasValue("Due Date", dueDate);

		super.clickOnListingRecord(invoiceRecordIndex);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("Registration Time", registrationTime);
		super.checkInputBoxHasValue("Due Date", dueDate);
		super.checkInputBoxHasValue("Quantity", quantity);
		super.checkInputBoxHasValue("Tax", tax);
		super.checkInputBoxHasValue("Link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/sponsor/invoice/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int sponsorshipRecordIndex, final int invoiceRecordIndex, final String code, final String sponsorShipCode, final String registrationTime, final String dueDate, final String quantity, final String tax,
		final String link) {

		super.signIn("sponsor1", "sponsor1");

		super.clickOnMenu("Sponsor", "SponsorShips");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(sponsorshipRecordIndex);
		super.clickOnButton("Invoices");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(invoiceRecordIndex);

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("Due Date", dueDate);
		super.fillInputBoxIn("Quantity", quantity);
		super.fillInputBoxIn("Tax", tax);
		super.fillInputBoxIn("Link", link);
		super.clickOnSubmit("Delete");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		//TEST delete with another rol 
		Collection<SponsorShip> sponsorShips;
		String param;

		sponsorShips = this.repository.findManySponsorShipsBySponsorUsername("sponsor1");
		for (final SponsorShip sponsorShip : sponsorShips) {
			param = String.format("masterId=%d", sponsorShip.getId());

			super.checkLinkExists("Sign in");
			super.request("/sponsor/invoices/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/sponsor/invoices/delete", param);
			super.checkPanicExists();
		}
		super.signOut();
	}

	@Test
	public void test301Hacking() {

		//TEST delete with !draftMode 
		Collection<SponsorShip> sponsorShips;
		String param;

		super.signIn("sponsor1", "sponsor1");
		sponsorShips = this.repository.findManySponsorShipsBySponsorUsername("sponsor1");
		for (final SponsorShip sponsorShip : sponsorShips)
			if (!sponsorShip.isDraftMode()) {
				param = String.format("masterId=%d", sponsorShip.getId());
				super.request("/sponsor/invoices/delete", param);
				super.checkPanicExists();
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {

		//TEST delete with another sponsor
		Collection<SponsorShip> sponsorShips;
		String param;

		super.signIn("sponsor1", "sponsor1");
		sponsorShips = this.repository.findManySponsorShipsBySponsorUsername("sponsor2");
		for (final SponsorShip sponsorShip : sponsorShips) {
			param = String.format("masterId=%d", sponsorShip.getId());
			super.request("/sponsor/invoices/delete", param);
			super.checkPanicExists();
		}
		super.signOut();
	}

}
