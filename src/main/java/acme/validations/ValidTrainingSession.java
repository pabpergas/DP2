package acme.validations;
import javax.validation.Payload;

public @interface ValidTrainingSession {
        String message() default "Invalid training session";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }