
package acme.testing.sponsor.sponsorShip;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class SponsorSponsorShipCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/sponsor/sponsorShip/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String project, final String startDate, final String endDate, final String amount, final String type, final String contactEmail, final String link) {

		String moment = "2022/07/30 00:00";
		super.signIn("sponsor1", "sponsor1");

		super.clickOnMenu("Sponsor", "SponsorShips");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("project", project);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("amount", amount);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("contactEmail", contactEmail);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

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

		super.clickOnButton("Invoices");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/sponsor/sponsorShip/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String project, final String startDate, final String endDate, final String amount, final String type, final String contactEmail, final String link) {

		super.signIn("sponsor1", "sponsor1");
		super.clickOnMenu("Sponsor", "SponsorShips");
		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("project", project);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("amount", amount);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("contactEmail", contactEmail);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		//TEST publish with another rol
		super.requestHome();
		super.checkLinkExists("Sign in");
		super.request("/sponsor/sponsor-ship/create");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/sponsor/sponsor-ship/create");
		super.checkPanicExists();
		super.signOut();

	}

}
