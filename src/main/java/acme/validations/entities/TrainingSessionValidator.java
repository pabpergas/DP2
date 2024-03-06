
package acme.validations.entities;

import java.time.LocalDate;
import java.time.ZoneId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import acme.entities.Sessions;
import acme.validations.ValidTrainingSession;

public class TrainingSessionValidator implements ConstraintValidator<ValidTrainingSession, Sessions> {

	@Override
	public void initialize(final ValidTrainingSession constraintAnnotation) {
	}

	@Override
	public boolean isValid(final Sessions sessions, final ConstraintValidatorContext constraintValidatorContext) {
		if (sessions == null)
			return true; // Null values should be handled by @NotNull

		LocalDate moduleCreationMoment = sessions.getTraining().getMoment().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate sessionStartDate = sessions.getStartDate();

		// Check if the session start date is at least one week ahead of the training module creation moment
		if (!sessionStartDate.isAfter(moduleCreationMoment.plusWeeks(1)))
			return false;

		// Check if the session duration is at least one week
		if (sessions.getDurationInWeeks() < 1)
			return false;

		return true;
	}
}
