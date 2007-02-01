/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: AccessDeniedException.java,v 1.1.1.1 2007-02-01 20:50:03 kherm Exp $
 */
package org.gridlab.gridsphere.portlet;

/**
 * The <code>AccessDeniedException</code> is thrown if a portlet attempts to access dynamic data in a manner
 * that it is not allowed to. For example, a portlet which is not in CONFIGURE mode cannot set
 * or remove attributes in the dynamic data of the portlet configuration. However, it can read
 * attributes. This exception is also thrown if a portlet tries to access an event based function
 * but is not during the event processing.
 */
public class AccessDeniedException extends PortletException {

    /**
     * Constructs instance of AccessDeniedException
     */
    public AccessDeniedException() {
        super();
    }

    /**
     * Constructs a new access denied exception with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text the exception text
     */
    public AccessDeniedException(String text) {
        super(text);
    }

}
