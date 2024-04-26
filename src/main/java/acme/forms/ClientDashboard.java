
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	double						totalNumProgressLogLessThan25;

	double						totalNumProgressLogBetween25And50;

	double						totalNumProgressLogBetween50And75;

	double						totalNumProgressLogAbove75;

	double						averageBudget;

	double						deviationBudget;

	double						minimumBudget;

	double						maximumBudget;

}
