
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class managerDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Integer						totalMusts;
	Integer						totalShoulds;
	Integer						totalCoulds;
	Integer						totalWonts;
	Double						averageEstimatedCostUserHistory;
	Double						deviationEstimatedCostUserHistory;
	Double						minimiunEstimatedCostUserHistory;
	Double						maximunEstimatedCostUserHistory;
	Double						averageEstimatedCostProject;
	Double						deviationEstimatedCostProject;
	Double						minimiunEstimatedCostProject;
	Double						maximunEstimatedCostProject;

}