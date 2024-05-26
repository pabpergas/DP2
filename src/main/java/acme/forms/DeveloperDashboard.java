
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Double						totalTrainingModules;
	Double						lastUpdateMoment;

	Double						totalTrainingSessions;
	Double						trainingSessionsLink;

	Double						averageTimeModule;
	Double						deviationTimeModule;
	Double						minimumTimeModule;
	Double						maximumTimeModule;

}
