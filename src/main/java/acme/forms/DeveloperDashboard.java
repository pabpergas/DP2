
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	int							totalTrainingModules;
	String						lastUpdateMoment;

	int							totalTrainingSessions;
	String						trainingSessionsLink;

	Double						averageTimeModule;
	Double						deviationTimeModule;
	Double						minimumTimeModule;
	Double						maximumTimeModule;

}
