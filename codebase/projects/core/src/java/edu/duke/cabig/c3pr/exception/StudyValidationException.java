package edu.duke.cabig.c3pr.exception;

/**
 * Exception thrown if study is not valid
 *
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 4, 2007
 * Time: 1:30:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudyValidationException extends java.lang.Exception{

    public StudyValidationException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public StudyValidationException(String s) {
        super(s);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public StudyValidationException(String s, Throwable throwable) {
        super(s, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
