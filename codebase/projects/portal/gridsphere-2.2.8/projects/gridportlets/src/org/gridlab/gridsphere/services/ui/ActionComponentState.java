/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionComponentState.java,v 1.1.1.1 2007-02-01 20:41:57 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui;

import java.util.Map;
import java.util.HashMap;

public class ActionComponentState {

    private Class component = null;
    private String action = null;
    private Map parameters = null;

    public ActionComponentState(Class actionClass) {
        this.component = actionClass;
        parameters = new HashMap();
    }

    public ActionComponentState(Class actionClass, String actionMethod) {
        component = actionClass;
        action = actionMethod;
        parameters = new HashMap();
    }

    public ActionComponentState(Class actionClass, Map actionParams) {
        component = actionClass;
        if (actionParams == null) {
            parameters = new HashMap();

        } else {
            parameters = actionParams;
        }
    }

    public ActionComponentState(Class actionClass, String actionMethod, Map actionParams) {
        component = actionClass;
        action = actionMethod;
        if (actionParams == null) {
            parameters = new HashMap();

        } else {
            parameters = actionParams;
        }
    }

    public Class getComponent() {
        return component;
    }

    public void setComponent(Class component) {
        this.component = component;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map getParameters() {
        return parameters;
    }

    public String getParameter(String name) {
        return (String)parameters.get(name);
    }

    public void addParameter(String name, Object value) {
        parameters.put(name, value);
    }

    public boolean equals(Object object){
        if ( this == object ) return true;
        if ( !(object instanceof ActionComponentState) ) return false;
        ActionComponentState state = (ActionComponentState)object;
        return (state.getAction().equals(action) && state.getComponent().equals(component));
    }

    public boolean equals(ActionComponentState state) {
        return (state.getAction().equals(action) && state.getComponent().equals(component));
    }
}
