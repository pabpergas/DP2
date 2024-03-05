package acme.validations.entities;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import acme.validations.BannerValidation;

@Constraint(validatedBy = BannerValidation.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface BannerValidator {
    String message() default "bad dates";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String initial();
    String start();
    String end();
}
