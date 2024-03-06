
package acme.forms;

import acme.client.data.AbstractForm;

public class ProgessLogDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Integer						totalProgressLogsBelow25;
	Integer						totalProgressLogs25To50;
	Integer						totalProgressLogs50To75;
	Integer						totalProgressLogsAbove75;
	Double						averageContractBudget;
	Double						deviationContractBudget;
	Double						minimumContractBudget;
	Double						maximumContractBudget;

}
