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