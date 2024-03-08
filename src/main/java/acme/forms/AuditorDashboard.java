
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Integer						totalNumberStaticAudits;
	Integer						totalNumberDinamicAudits;

	Integer						minNumberRecords;
	Integer						maxNumberRecords;
	Double						averageNumberRecords;
	Double						deviationNumberRecords;

	Double						minPeriodRecord;
	Double						maxPeriodRecord;
	Double						averagePeriodRecord;
	Double						deviationPeriodRecord;
}
