package edu.duke.cabig.c3pr.exception;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Oct 16, 2007
 * Time: 2:13:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class XMLValidationException extends C3PRBaseException {


    public XMLValidationException(String string) {
        super(string);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public XMLValidationException(String string, Throwable throwable) {
        super(string, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
