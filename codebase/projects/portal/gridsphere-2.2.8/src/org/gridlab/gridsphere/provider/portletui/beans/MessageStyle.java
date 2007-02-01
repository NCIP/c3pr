/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: MessageStyle.java,v 1.1.1.1 2007-02-01 20:51:09 kherm Exp $
 */

package org.gridlab.gridsphere.provider.portletui.beans;

/**
 * The <code>MessageStyle</code> represents portlet text style to be displayed
 */
public class MessageStyle  {

    // CSS definitions according to Portlet API spec. PLT.C
    public static final String MSG_STATUS = "portlet-msg-status";
    public static final String MSG_INFO = "portlet-msg-info";
    public static final String MSG_ERROR = "portlet-msg-error";
    public static final String MSG_ALERT = "portlet-msg-alert";
    public static final String MSG_SUCCESS = "portlet-msg-success";
    public static final String MSG_ITALIC = "italic";
    public static final String MSG_BOLD = "bold";
    public static final String MSG_UNDERLINE = "underline";
}
