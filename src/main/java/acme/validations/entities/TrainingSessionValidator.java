
package acme.validations.entities;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import acme.entities.S3.Sessions;
import acme.validations.ValidTrainingSession;

public class TrainingSessionValidator implements ConstraintValidator<ValidTrainingSession, Sessions> {

	@Override
	public void initialize(final ValidTrainingSession constraintAnnotation) {
	}

	@Override
	public boolean isValid(final Sessions sessions, final ConstraintValidatorContext constraintValidatorContext) {
		if (sessions == null)
			return true;

		Date moduleCreationMoment = sessions.getTraining().getMoment();
		Date sessionStartDate = sessions.getStartDate();

		if (sessionStartDate.before(Date.from(moduleCreationMoment.toInstant().plusSeconds(60 * 60 * 24 * 7))))
			return false;

		if (sessions.getDurationInWeeks() < 1)
			return false;

		return true;
	}
}
