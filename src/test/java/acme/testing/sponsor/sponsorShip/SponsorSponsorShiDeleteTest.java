
package acme.testing.sponsor.sponsorShip;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.S4.SponsorShip;
import acme.testing.TestHarness;

public class SponsorSponsorShiDeleteTest extends TestHarness {
	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorShipTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/sponsor/sponsorShip/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String project, final String moment, final String startDate, final String endDate, final String amount, final String type, final String contactEmail, final String link) {
		// HINT: this test logs in as an employer, lists his or her jobs, 
		// HINT+ selects one of them, updates it, and then checks that 
		// HINT+ the update has actually been performed.

		super.signIn("sponsor1", "sponsor1");

		super.clickOnMenu("Sponsor", "SponsorShips");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("project", project);
		super.fillInputBoxIn("moment", moment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("amount", amount);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("contactEmail", contactEmail);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Delete");

		super.checkListingExists();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/employer/job/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String reference, final String contractor, final String title, final String deadline, final String salary, final String score, final String moreInfo, final String description) {
		// HINT: this test attempts to update a job with wrong data.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a job with a role other than "Employer",
		// HINT+ or using an employer who is not the owner.

		Collection<SponsorShip> sponsorShips;
		String param;

		sponsorShips = this.repository.findManySponsorShipsBySponsorUsername("sponsor1");
		for (final SponsorShip sponsorShip : sponsorShips) {
			param = String.format("id=%d", sponsorShip.getId());

			super.checkLinkExists("Sign in");
			super.request("/sponsor/sponsorShip/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/sponsor/sponsorShip/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("sponsor2", "sponsor2");
			super.request("/employer/job/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("worker1", "worker1");
			super.request("/employer/job/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
