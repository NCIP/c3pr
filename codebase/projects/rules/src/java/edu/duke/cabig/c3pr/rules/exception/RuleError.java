/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.rules.exception;

/**
 * @author Rhett Sutphin
 */
public class RuleError extends Error {
    public RuleError(String message) {
        super(message);
    }

    public RuleError(String message, Throwable cause) {
        super(message, cause);
    }
}
