
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// total number of progress logs with a completeness rate below 25%
	int							totalNumProgressLogLessThan25;

	// total number of progress logs with a completeness rate between 25% and 50%
	int							totalNumProgressLogBetween25And50;

	// total number of progress logs with a completeness rate between 50% and 75%
	int							totalNumProgressLogBetween50And75;

	// total number of progress logs with a completeness rate above 75%
	int							totalNumProgressLogAbove75;

	// average, deviation, minimum, and maximum budget of the contracts

	Map<String, Double>			averagePerCurrency;

	Map<String, Double>			deviationPerCurrency;

	Map<String, Double>			maximumPerCurrency;

	Map<String, Double>			minimumPerCurrency;

	String[]					supportedCurrencies;

}
