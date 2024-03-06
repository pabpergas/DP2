
package acme.forms;

import acme.client.data.AbstractForm;

public class ProgessLogDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	int							totalProgressLogsBelow25;
	int							totalProgressLogs25To50;
	int							totalProgressLogs50To75;
	int							totalProgressLogsAbove75;
	Double						averageContractBudget;
	Double						deviationContractBudget;
	Double						minimumContractBudget;
	Double						maximumContractBudget;

}
