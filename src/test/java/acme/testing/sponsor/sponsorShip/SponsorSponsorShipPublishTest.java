
package acme.testing.sponsor.sponsorShip;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.S4.SponsorShip;
import acme.testing.TestHarness;

public class SponsorSponsorShipPublishTest extends TestHarness {
	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorShipTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/sponsor/sponsorShip/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String project, final String moment, final String startDate, final String endDate, final String amount, final String type, final String contactEmail, final String link) {

		super.signIn("sponsor1", "sponsor1");

		super.clickOnMenu("Sponsor", "SponsorShips");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, project);
		super.checkColumnHasValue(recordIndex, 1, code);
		super.checkColumnHasValue(recordIndex, 2, moment);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkAlertExists(false);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/sponsor/sponsorShip/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String reference, final String contractor, final String title, final String deadline, final String salary, final String score, final String moreInfo, final String description) {
		// HINT: this test attempts to update a job with wrong data.

		super.signIn("sponsor1", "sponsor1");

		super.clickOnMenu("Sponsor", "SponsorShips");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, reference);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("reference", reference);
		super.fillInputBoxIn("contractor", contractor);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("deadline", deadline);
		super.fillInputBoxIn("salary", salary);
		super.fillInputBoxIn("score", score);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("description", description);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		//TEST publish with another rol
		Collection<SponsorShip> sponsorShips;
		String params;

		sponsorShips = this.repository.findManySponsorShipsBySponsorUsername("sponsor1");
		for (final SponsorShip sponsorShip : sponsorShips)
			if (sponsorShip.isDraftMode()) {
				params = String.format("id=%d", sponsorShip.getId());

				super.checkLinkExists("Sign in");
				super.request("/sponsor/sponsor-ship/publish", params);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/sponsor/sponsor-ship/publish", params);
				super.checkPanicExists();
				super.signOut();

			}
	}

	@Test
	public void test301Hacking() {

		//TEST publish with !draftMode 
		Collection<SponsorShip> sponsorShips;
		String params;
		super.signIn("sponsor1", "sponsor1");
		sponsorShips = this.repository.findManySponsorShipsBySponsorUsername("sponsor1");
		for (final SponsorShip sponsorShip : sponsorShips)
			if (!sponsorShip.isDraftMode()) {
				params = String.format("id=%d", sponsorShip.getId());
				super.request("/sponsor/sponsor-ship/publish", params);
			}
		super.signOut();
	}

	@Test
	public void test302Hacking() {

		//TEST publish with another sponsor
		Collection<SponsorShip> sponsorShips;
		String params;

		super.signIn("sponsor2", "sponsor2");
		sponsorShips = this.repository.findManySponsorShipsBySponsorUsername("sponsor1");
		for (final SponsorShip sponsorShip : sponsorShips) {

			params = String.format("id=%d", sponsorShip.getId());
			super.request("/sponsor/sponsor-ship/publish", params);
		}
		super.signOut();
	}

}
