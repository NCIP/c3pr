/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.rules.exception;


/**
 * @author un-ascribed
 */
public class RuleException extends Exception {

	public RuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleException(Throwable cause) {
        super("Rule Exception", cause);
    }

}
