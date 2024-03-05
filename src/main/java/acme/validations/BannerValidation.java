package acme.validations;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import acme.validations.entities.BannerValidator;

public class BannerValidation implements ConstraintValidator<BannerValidator, Object>{
    private String initialFieldName;
    private String startFieldName;
    private String endFieldName;

    @Override
    public void initialize(BannerValidator constraintAnnotation) {
        initialFieldName  = constraintAnnotation.initial();
        startFieldName = constraintAnnotation.start();
        endFieldName = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
       try {
            Field initialDateField = value.getClass().getDeclaredField(initialFieldName);
            initialDateField.setAccessible(true);

            final Field startDateField = value.getClass().getDeclaredField(startFieldName);
            startDateField.setAccessible(true);

            final Field endDateField = value.getClass().getDeclaredField(endFieldName);
            endDateField.setAccessible(true);

            final LocalDateTime initialDate = (LocalDateTime) initialDateField.get(value);
            final LocalDateTime startDate = (LocalDateTime) startDateField.get(value);
            final LocalDateTime endDate = (LocalDateTime) endDateField.get(value);
            final long days = startDate.until(endDate, ChronoUnit.DAYS);

            return startDate.isAfter(initialDate) && startDate.isBefore(endDate) && days >= 7;

       } catch (NoSuchFieldException | IllegalAccessException ignored) {
        return false;
        }
    }
}