package edu.duke.cabig.c3pr.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import edu.duke.cabig.c3pr.annotations.validator.UniqueEmailAddressForResearchStaffValidator;
import edu.duke.cabig.c3pr.annotations.validator.ValidatorClass;

/**
 * Research staff must have unique email address. This is required to create csm-user etc..
 * 
 * 
 */
@Documented
@ValidatorClass(UniqueEmailAddressForResearchStaffValidator.class)
@Target( { METHOD, FIELD, ElementType.PARAMETER })
@Retention(RUNTIME)
public @interface UniqueEmailAddressForResearchStaff {
    public abstract String message() default "email address already exists in the database..!";

}