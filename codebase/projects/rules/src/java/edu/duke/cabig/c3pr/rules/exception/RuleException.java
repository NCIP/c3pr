package edu.duke.cabig.c3pr.rules.exception;


/**
 * @author un-ascribed
 */
public class RuleException extends Exception {

    /*public RuleException(String message, Throwable cause) {
        this("RULE-001", message, cause);
    }

    public RuleException(Throwable cause) {
        this("RULE-001", "Rule Exception", cause);
    }

    public RuleException(String code, String message) {
        this(code, message, null);
    }

    public RuleException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }*/
	
	public RuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleException(Throwable cause) {
        super("Rule Exception", cause);
    }

}