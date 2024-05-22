
package acme.testing.sponsor.sponsorShip;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.S4.SponsorShip;
import acme.testing.TestHarness;

public class SponsorSponsorShipUpdateTest extends TestHarness {
	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorShipTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/sponsor/sponsorShip/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String project, final String moment, final String startDate, final String endDate, final String amount, final String type, final String contactEmail, final String link) {

		super.signIn("sponsor1", "sponsor1");

		super.clickOnMenu("Sponsor", "SponsorShips");
		super.checkListingExists();

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("project", project);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("amount", amount);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("contactEmail", contactEmail);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.clickOnMenu("Sponsor", "SponsorShips");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, project);
		super.checkColumnHasValue(recordIndex, 1, code);
		super.checkColumnHasValue(recordIndex, 2, moment);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("project", project);
		super.checkInputBoxHasValue("moment", moment);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("amount", amount);
		super.checkInputBoxHasValue("type", type);
		super.checkInputBoxHasValue("contactEmail", contactEmail);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/sponsor/sponsorShip/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String project, final String moment, final String startDate, final String endDate, final String amount, final String type, final String contactEmail, final String link) {

		super.signIn("sponsor1", "sponsor1");

		super.clickOnMenu("Sponsor", "SponsorShips");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("project", project);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("amount", amount);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("contactEmail", contactEmail);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		//TEST update with another rol 
		Collection<SponsorShip> sponsorShips;
		String param;

		sponsorShips = this.repository.findManySponsorShipsBySponsorUsername("sponsor1");
		for (final SponsorShip sponsorShip : sponsorShips) {
			param = String.format("id=%d", sponsorShip.getId());

			super.requestHome();
			super.checkLinkExists("Sign in");
			super.request("/sponsor/sponsorShip/update", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/sponsor/sponsor-ship/update", param);
			super.checkPanicExists();
			super.signOut();

		}
	}

	@Test
	public void test301Hacking() {

		//TEST update with !draftMode 
		Collection<SponsorShip> sponsorShips;
		String params;
		super.signIn("sponsor1", "sponsor1");
		sponsorShips = this.repository.findManySponsorShipsBySponsorUsername("sponsor1");
		for (final SponsorShip sponsorShip : sponsorShips)
			if (!sponsorShip.isDraftMode()) {
				params = String.format("id=%d", sponsorShip.getId());
				super.request("/sponsor/sponsor-ship/update", params);
				super.checkPanicExists();

			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {

		//TEST update with another sponsor
		Collection<SponsorShip> sponsorShips;
		String params;

		super.signIn("sponsor2", "sponsor2");
		sponsorShips = this.repository.findManySponsorShipsBySponsorUsername("sponsor1");
		for (final SponsorShip sponsorShip : sponsorShips) {

			params = String.format("id=%d", sponsorShip.getId());
			super.request("/sponsor/sponsor-ship/update", params);
			super.checkPanicExists();

		}
		super.signOut();
	}

}
