package edu.duke.cabig.c3pr.rules.exception;

public class RuleSetNotFoundException extends RuleException {

    /*public RuleSetNotFoundException(String message, Throwable cause) {
        super("RULE-100", message, cause);
    }

    public RuleSetNotFoundException(Throwable t) {
        super("RULE-100", "Unable to find the deployed rule set", t);
    }

    public RuleSetNotFoundException(String message) {
        super("RULE-100", message);
    }*/
	
	public RuleSetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleSetNotFoundException(Throwable t) {
        super("Unable to find the deployed rule set", t);
    }

}
