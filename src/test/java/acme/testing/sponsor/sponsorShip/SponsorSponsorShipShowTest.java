
package acme.testing.sponsor.sponsorShip;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.S4.SponsorShip;
import acme.testing.TestHarness;

public class SponsorSponsorShipShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorShipTestRepository repository;

	// Test data --------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/sponsor/sponsorShip/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String project, final String moment, final String startDate, final String endDate, final String amount, final String type, final String contactEmail, final String link) {
		// HINT: this test signs in as an employer, lists all of the jobs, click on  
		// HINT+ one of them, and checks that the form has the expected data.

		super.signIn("sponsor1", "sponsor1");

		super.clickOnMenu("Sponsor", "SponsorShips");
		super.sortListing(0, "asc");
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

	@Test
	public void test200Negative() {
		// HINT: there are not any negative tests for this feature because it is a listing
		// HINT+ that does not involve entering any data in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show an unpublished job by someone who is not the principal.

		Collection<SponsorShip> sponsorShips;
		String param;

		sponsorShips = this.repository.findManySponsorShipsBySponsorUsername("sponsor1");
		for (final SponsorShip sponsorShip : sponsorShips)
			if (sponsorShip.isDraftMode()) {
				param = String.format("id=%d", sponsorShip.getId());

				super.checkLinkExists("Sign in");
				super.request("/sponsor/sponsor-ship/show", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/sponsor/sponsor-ship/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("sponsor2", "sponsor2");
				super.request("/sponsor/sponsor-ship/show", param);
				super.checkPanicExists();
				super.signOut();

			}
	}

}
