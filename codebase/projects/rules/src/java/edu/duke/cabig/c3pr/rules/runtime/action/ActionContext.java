package edu.duke.cabig.c3pr.rules.runtime.action;

import gov.nih.nci.cabig.c3pr.rules.brxml.Action;

/**
 *
 * @author Sujith Vellat Thayyilthodi
 */
public class ActionContext {

    private Action action;

    private String actionId;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

}