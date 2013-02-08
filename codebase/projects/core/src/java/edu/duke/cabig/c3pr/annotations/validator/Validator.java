/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.annotations.validator;

import java.lang.annotation.Annotation;

/**
 * A constraint validator for a particular annotation
 * 
 */
public interface Validator<A extends Annotation> {
    /**
     * does the object/element pass the constraints
     */
    public boolean validate(Object value);

    /**
     * Take the annotations values
     * 
     * @param parameters
     */
    public void initialize(A parameters);

    public String message();
}
