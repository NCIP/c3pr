package edu.duke.cabig.c3pr.annotations.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import edu.duke.cabig.c3pr.annotations.validator.Validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Link between an constraint annotation and it's validator implementation
 * 
 * 
 */
@Documented
@Target( { ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidatorClass {
    Class<? extends Validator> value();
}
