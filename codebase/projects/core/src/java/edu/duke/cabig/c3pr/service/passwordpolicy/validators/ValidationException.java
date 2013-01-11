/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.service.passwordpolicy.validators;

import edu.duke.cabig.c3pr.exception.C3PRBaseException;

public class ValidationException extends C3PRBaseException {
    private static final long serialVersionUID = 8625024692592257767L;

    public ValidationException(String message) {
        super(message);
    }
}
