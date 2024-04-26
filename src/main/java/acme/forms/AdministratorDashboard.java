
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	int							totalPrincipalsWithRole;
	Double						ratioNoticesWithEmailAndLink;
	Double						ratioCriticalObjectives;
	Double						ratioNonCriticalObjectives;
	Double						averageValueInRisks;
	Double						minimumValueInRisks;
	Double						maximumValueInRisks;
	Double						standardDeviationValueInRisks;
	Double						averageNumberOfClaimsLast10Weeks;
	Double						minimumNumberOfClaimsLast10Weeks;
	Double						maximumNumberOfClaimsLast10Weeks;
	Double						standardDeviationNumberOfClaimsLast10Weeks;

}
