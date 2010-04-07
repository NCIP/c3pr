package edu.duke.cabig.c3pr.rules.exception;

public class RuleSetNotFoundException extends RuleException {

	public RuleSetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleSetNotFoundException(Throwable t) {
        super("Unable to find the deployed rule set", t);
    }

}
