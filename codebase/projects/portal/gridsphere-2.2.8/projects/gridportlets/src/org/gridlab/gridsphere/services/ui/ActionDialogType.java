/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionDialogType.java,v 1.1.1.1 2007-02-01 20:41:58 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui;

public class ActionDialogType {

    public static final ActionDialogType OK_TYPE = new ActionDialogType("OK_TYPE");
    public static final ActionDialogType OK_CANCEL_TYPE = new ActionDialogType("OK_CANCEL_TYPE");
    public static final ActionDialogType YES_NO_TYPE = new ActionDialogType("YES_NO_TYPE");
    public static final ActionDialogType YES_NO_CANCEL_TYPE = new ActionDialogType("YES_NO_CANCEL_TYPE");

    private final String myName; // for debug only

    private ActionDialogType(String name) {
        myName = name;
    }

    public String toString() {
        return myName;
    }
}
