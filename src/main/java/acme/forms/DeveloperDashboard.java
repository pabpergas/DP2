
package acme.forms;

import javax.validation.constraints.NotNull;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	Integer						totalTrainingModules;
	String						lastUpdateMoment;

	@NotNull
	Integer						totalTrainingSessions;
	String						trainingSessionsLink;

	Double						averageTimeModule;
	Double						deviationTimeModule;
	Double						minimumTimeModule;
	Double						maximumTimeModule;

}
