package com.oakcity.nicknack.server.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;

@NotNull
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ActionParameterValidator.class)
@Documented
public @interface ValidActionParameter {

    String message() default "Action is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    

}