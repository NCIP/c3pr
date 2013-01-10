/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
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
