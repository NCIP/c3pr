/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.rules.exception;

public class RuleSetNotFoundException extends RuleException {

	public RuleSetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleSetNotFoundException(Throwable t) {
        super("Unable to find the deployed rule set", t);
    }

}
