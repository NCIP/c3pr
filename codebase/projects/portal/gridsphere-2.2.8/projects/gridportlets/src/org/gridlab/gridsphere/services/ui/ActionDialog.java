/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionDialog.java,v 1.1.1.1 2007-02-01 20:41:57 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import javax.portlet.PortletException;
import java.util.Map;
import java.util.HashMap;

public class ActionDialog extends ActionComponent {

    private transient static PortletLog log = SportletLog.getInstance(ActionDialog.class);

    protected ActionComponentFrame root = null;
    protected ActionComponentState rootState = null;
    protected ActionComponentState returnState = null;
    protected ActionComponentFrame baseContainer = null;
    protected ActionDialogType dialogType = ActionDialogType.OK_TYPE;
    protected ActionComponentState okNextState = null;
    protected ActionComponentState cancelNextState = null;
    protected ActionComponentState yesNextState = null;
    protected ActionComponentState noNextState = null;
    protected boolean isOpen = false;

    protected String message = null;
    protected TextBean messageText = null;

    public final static String OK_PAGE = "/jsp/action/OkDialog.jsp";
    public final static String OK_CANCEL_PAGE = "/jsp/action/OkCancelDialog.jsp";
    public final static String YES_NO_PAGE = "/jsp/action/YesNoDialog.jsp";
    public final static String YES_NO_CANCEL_PAGE = "/jsp/action/YesNoCancelDialog.jsp";
    protected String okPage = null;
    protected String okCancelPage = null;
    protected String yesNoPage = null;
    protected String yesNoCancelPage = null;

    public final static String OK_ACTION = "doOk";
    public final static String CANCEL_ACTION = "doCancel";
    public final static String YES_ACTION = "doYes";
    public final static String NO_ACTION = "doNo";

    public ActionDialog(ActionComponentFrame container, String compName) {
        super(container, compName);
        ActionComponentState currentState = container.getNextState();
        returnState = new ActionComponentState(currentState.getComponent(),
                                               RENDER_ACTION);
        messageText = createTextBean("messageText");
        setDialogType(ActionDialogType.OK_TYPE);
        log.debug("return state for dialog " + compId + " is " + returnState.getComponent().getName() + " " + returnState.getAction());
    }

    public ActionComponentFrame getBaseContainer() {
        return baseContainer;
    }

    public void setBaseContainer(ActionComponentFrame baseContainer) {
        this.baseContainer = baseContainer;
    }

    public String getOkPage() {
        if (okPage == null) {
            return OK_PAGE;
        }
        return okPage;
    }

    public String getOkCancelPage() {
        if (okCancelPage == null) {
            return OK_CANCEL_PAGE;
        }
        return okCancelPage;
    }

    public String getYesNoPage() {
        if (yesNoPage == null) {
            return YES_NO_PAGE;
        }
        return yesNoPage;
    }

    public String getYesNoCancelPage() {
        if (yesNoCancelPage == null) {
            return YES_NO_CANCEL_PAGE;
        }
        return yesNoCancelPage;
    }

    public String getMessageKey() {
        return messageText.getKey();
    }

    public void setMessageKey(String key) {
        messageText.setKey(key);
    }

    public String getMessageValue() {
        return messageText.getValue();
    }

    public void setMessageValue(String value) {
        messageText.setValue(value);
    }

    public String getMessageStyle() {
        return messageText.getStyle();
    }

    public void setMessageStyle(String value) {
        messageText.setStyle(value);
    }

    public void showOkDialog(ActionComponentState okNextState) throws PortletException {
        log.debug("showOkDialog");
        this.okNextState = okNextState;
        openDialog(ActionDialogType.OK_TYPE);
    }

    public void showOkCancelDialog(ActionComponentState okNextState,
                                   ActionComponentState cancelNextState) throws PortletException {
        this.okNextState = okNextState;
        this.cancelNextState = cancelNextState;
        openDialog(ActionDialogType.OK_CANCEL_TYPE);
    }

    public void showYesNoDialog(ActionComponentState yesNextState,
                                ActionComponentState noNextState) throws PortletException {
        this.yesNextState = yesNextState;
        this.noNextState = noNextState;
        openDialog(ActionDialogType.YES_NO_TYPE);
    }

    public void showYesNoCancelDialog(ActionComponentState yesNextState,
                                      ActionComponentState noNextState,
                                      ActionComponentState cancelNextState) throws PortletException {
        this.yesNextState = yesNextState;
        this.noNextState = noNextState;
        this.cancelNextState = cancelNextState;
        openDialog(ActionDialogType.YES_NO_CANCEL_TYPE);
    }

    protected void openDialog(ActionDialogType dialogType) throws PortletException {
        log.debug("openDialog");
        // Dialog type determines default jsp and action
        this.dialogType = dialogType;
        if (dialogType.equals(ActionDialogType.OK_CANCEL_TYPE)) {
            setDefaultJspPage(getOkCancelPage());
        } else if (dialogType.equals(ActionDialogType.YES_NO_TYPE)) {
            setDefaultJspPage(getYesNoPage());
        } else if (dialogType.equals(ActionDialogType.YES_NO_CANCEL_TYPE)) {
            setDefaultJspPage(getYesNoCancelPage());
        } else {
            setDefaultJspPage(getOkPage());
        }
        setDefaultAction("doView");
        // Init and load dialog component
        init(container.getPortletRequest(), portletResponse);
        load(container.getPortletRequest(), portletResponse);
        // Do default action....
        container.doAction(getClass(), defaultAction, new HashMap());
    }

    public void setDialogType(ActionDialogType dialogType) {
    }

    public void doView(Map parameters) throws PortletException {
        log.debug("doView");
        // Do view action
        if (doViewAction(parameters)) {
            isOpen = true;
            setNextState(defaultJspPage);
        }
    }

    public void doOk(Map parameters) throws PortletException {
        log.debug("doOk");
        if (doOkAction(parameters)) {
            closeDialog(okNextState);
        }
    }

    public void doCancel(Map parameters) throws PortletException {
        if (doCancelAction(parameters)) {
            closeDialog(cancelNextState);
        }
    }

    public void doYes(Map parameters) throws PortletException {
        if (doYesAction(parameters)) {
            closeDialog(yesNextState);
        }
    }

    public void doNo(Map parameters) throws PortletException {
        if (doNoAction(parameters)) {
            closeDialog(noNextState);
        }
    }

    protected void closeDialog(ActionComponentState baseState) throws PortletException {
        log.debug("closeDialog");
        // Re-render return component
        setNextState(returnState);
        isOpen = false;
        log.debug("base container for dialog " + compId + " is " + baseContainer.getComponentId());
        log.debug("base state for dialog " + compId + " is " + baseState.getComponent().getName() + " " + baseState.getAction());
        // Must init / load base container if not same as our container
        if (! baseContainer.getComponentId().equals(container.getComponentId()) ) {
            baseContainer.init(container.getPortletRequest(), portletResponse);
            baseContainer.load(container.getPortletRequest(), portletResponse);
        }
        // Preforming base return action
        baseContainer.doAction(baseState.getComponent(),
                               baseState.getAction(),
                               baseState.getParameters());
    }

    public boolean doViewAction(Map parameters) throws PortletException {
        return true;
    }

    public boolean doOkAction(Map parameters) throws PortletException {
        return true;
    }

    public boolean doCancelAction(Map parameters) throws PortletException {
        return true;
    }

    public boolean doYesAction(Map parameters) throws PortletException {
        return true;
    }

    public boolean doNoAction(Map parameters) throws PortletException {
        return true;
    }
}
