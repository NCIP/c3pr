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
