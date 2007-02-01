/*
* @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
* @version $Id: PortletException.java,v 1.1.1.1 2007-02-01 20:50:05 kherm Exp $
*/
package org.gridlab.gridsphere.portlet;

import javax.servlet.ServletException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>PortletException</code> class defines a general exception that a
 * portlet can throw when it encounters an exceptional condition.
 */
public class PortletException extends ServletException {

    private Throwable cause = null;
    private String text = "";

    /**
     * Constructs an instance of PortletException
     */
    public PortletException() {
        super();
    }

    /**
     * Constructs an instance of PortletException with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text the exception text
     */
    public PortletException(String text) {
        super(text);
        this.text = text;
    }

    /**
     * Constructs a new portlet exception with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text  the exception text
     * @param cause the root cause
     */
    public PortletException(String text, Throwable cause) {
        super(text, cause);
        this.text = text;
        this.cause = cause;
    }

    /**
     * Constructs a new portlet exception when the portlet needs to throw an exception.
     * The exception's message is based on the localized message of the underlying exception.
     *
     * @param cause the root cause
     */
    public PortletException(Throwable cause) {
        super(cause);
        this.cause = cause;
        text = cause.getMessage();
    }

    /**
     * Return the exception message
     *
     * @return the exception message
     */
    public String getMessage() {
        return text;
    }

    public Throwable getCause() {
        return ((cause != null) ? cause.getCause() : null);
    }

    public void printStackTrace() {
        if (cause != null) {
            cause.printStackTrace();
        }
    }

    public void printStackTrace(PrintStream ps) {
        if (text != null) ps.println(text);
        if (cause != null) {
            ps.println("Caused by:");
            cause.printStackTrace(ps);
        }
    }

    public void printStackTrace(PrintWriter pw) {
        if (text != null) pw.println(text);
        if (cause != null) {
            pw.println("Caused by:");
            cause.printStackTrace(pw);
        }
    }

    /**
     * Returns the exception that caused this servlet exception.
     *
     *
     * @return                  the <code>Throwable</code>
     *                          that caused this servlet exception
     *
     */

    public Throwable getRootCause() {
        return cause;
    }

}
