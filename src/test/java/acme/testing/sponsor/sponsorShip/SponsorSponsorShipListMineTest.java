
package acme.testing.sponsor.sponsorShip;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class SponsorSponsorShipListMineTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/sponsor/sponsorShip/list-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String reference, final String projectTitle, final String code, final String moment) {
		// HINT: this test authenticates as an employer, lists his or her jobs only,
		// HINT+ and then checks that the listing has the expected data.

		super.signIn("sponsor1", "sponsor1");

		super.clickOnMenu("Sponsor", "SponsorShips");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, projectTitle);
		super.checkColumnHasValue(recordIndex, 1, code);
		super.checkColumnHasValue(recordIndex, 2, moment);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there are not any negative tests for this feature since it is a listing that
		// HINT+ does not involve entering any data into any forms.
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/sponsor/sponsor-ship/list-mine");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/sponsor/sponsor-ship/list-mine");
		super.checkPanicExists();
		super.signOut();

	}

}
