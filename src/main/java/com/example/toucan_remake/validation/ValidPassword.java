package com.example.toucan_remake.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation for validate password while creating account.
 * Usages: Spring Boot Validation, Passay
 * @author Jakub Iwanicki
 */
@Documented
@Constraint(validatedBy = RegistrationValidator.class)
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "invalid password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
