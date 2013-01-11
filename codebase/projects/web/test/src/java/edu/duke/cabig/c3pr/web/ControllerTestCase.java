/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @author Priyatam
 */
public abstract class ControllerTestCase extends WebTestCase {
    protected static void assertNoBindingErrorsFor(String fieldName, Map<String, Object> model) {
        BindingResult result = (BindingResult) model
                        .get(BindingResult.MODEL_KEY_PREFIX + "command");
        List<FieldError> fieldErrors = result.getFieldErrors(fieldName);
        assertEquals("There were errors for field " + fieldName + ": " + fieldErrors, 0,
                        fieldErrors.size());
    }
}
