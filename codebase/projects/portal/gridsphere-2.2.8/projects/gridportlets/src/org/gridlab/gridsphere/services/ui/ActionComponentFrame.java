/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionComponentFrame.java,v 1.1.1.1 2007-02-01 20:41:57 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui;

import org.gridlab.gridsphere.provider.portletui.beans.ActionComponentBean;
import org.gridlab.gridsphere.portlet.PortletLog;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;

import javax.portlet.PortletResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletException;
import java.util.*;

public class ActionComponentFrame extends ActionComponentBean {

    public static final String DEFAULT_ACTION_METHOD = "+default+";
    public static final String UNDEFINED_ACTION_METHOD = "+undefined+";

    private static PortletLog log = SportletLog.getInstance(ActionComponentFrame.class);

    protected ActionComponent parent = null;
    protected String compId = "";
    protected String compPrefix = "";

    protected PortletRequest portletRequest = null;
    protected PortletResponse portletResponse = null;
    protected ActionComponentState lastActionState = null;
    protected ActionComponentState nextActionState = null;
    protected boolean isDisplayed = false;

    protected ActionComponent activeComp = null;
    protected Hashtable actionComps = new Hashtable();
    protected Hashtable actionCompNames = new Hashtable();
    protected Hashtable actionFilterMap = new Hashtable();
    protected Hashtable actionLinks = new Hashtable();
    protected int numberOfComponents = 0;

    /**
     * Constructs default include bean
     */
    public ActionComponentFrame() {
        super();
    }

    /**
     * Constructs default include bean
     */
    public ActionComponentFrame(PortletRequest request, String beanId) {
        portletRequest = request;
        this.beanId = beanId;
        compId = beanId;
        compPrefix = compId + '%';
    }

    /**
     * Constructs an include bean
     */
    public ActionComponentFrame(ActionComponent comp, String beanId) {
        this.beanId = beanId;
        compId = comp.getComponentId() +  '%' + beanId;
        compPrefix = compId + '%';
        parent = comp;
    }

    public PortletRequest getPortletRequest() {
//        return (PortletRequest)request;
        return portletRequest;
    }

    public void setPortletRequest(PortletRequest request) {
//        this.request = request;
        portletRequest = request;
    }

    public PortletResponse getPortletRespone() {
        return portletResponse;
    }

    public ActionComponent getParentComponent() {
        return parent;
    }

    public String getComponentId() {
        return compId;
    }

    public String getWidgetKey() {
//        String cid = (String) request.getAttribute(SportletProperties.COMPONENT_ID);
        String cid = (String) portletRequest.getAttribute(SportletProperties.COMPONENT_ID);
        String beanKey = null;
        if (compId == null) {
            beanKey = beanId + '_' + cid;
        } else {
            beanKey = compId + '_' + cid;
        }
        log.debug("getWidgetKey(" + beanId + ") = " + beanKey);
        return beanKey;
    }

    public ActionComponent getActiveComponent() {
        return activeComp;
    }

    public boolean containsForms() {
        if (activeComp == null) {
            return false;
        }
        return activeComp.containsForms();
    }

    public void init(PortletRequest request, PortletResponse response) throws PortletException {

        log.debug("ActionComponentFrame.init(" + compId + ") request params " + request.getParameterMap().size());

        log.debug("init(" + compId + ")");
//        this.request = request;
        portletRequest = request;
        portletResponse = response;
        if (nextActionState == null) {
            throw new PortletException("Next action state has not been set!");
        }
        // We are not displayed yet...
        isDisplayed = false;
        // Get active component
        if (activeComp == null) {
            activeComp = getActionComponent(nextActionState);
            activeCompId = activeComp.getComponentId();
        }
        // Init active component
        try {
            activeComp.init(request, response);
        } catch (PortletException e) {
            log.error("Unable to init action component " + activeComp.getComponentId() , e);
            throw e;
        }
    }

    public void load(PortletRequest request, PortletResponse response) throws PortletException {

        log.debug("ActionComponentFrame.load(" + compId + ") request params " + request.getParameterMap().size());

        log.debug("load(" + compId + ")");
        if (activeComp == null) {
            throw new PortletException("No action component has been set!");
        }
        try {
            activeComp.load(request, response);
        } catch (PortletException e) {
            log.error("Unable to load action component " + activeComp.getComponentId() , e);
            throw e;
        }
    }

    public void store() {
        log.debug("store(" + compId + ")");
//        request.setAttribute(getWidgetKey(), this);
        portletRequest.setAttribute(getWidgetKey(), this);
    }

    public void registerActionLink(ActionComponent srcComp,
                                   String srcMethod,
                                   Class dstClass) throws PortletException {
        registerActionLink(srcComp, srcMethod, dstClass, srcMethod);
    }

    public void registerActionLink(ActionComponent srcComp,
                                   String srcMethod,
                                   Class dstClass,
                                   String dstMethod) throws PortletException {
        String srcCompName = srcComp.getComponentName();
        String srcAction = srcCompName + '%' + srcMethod;
        String dstAction = (String)actionLinks.get(srcAction);
        log.debug("Creating action link for " + srcAction);
        ActionComponentState dstState = new ActionComponentState(dstClass, dstMethod);
        //ActionComponent dstComp = getActionComponent(dstClass);
        ActionComponent dstComp = getActionComponent(dstState);
        dstAction = dstComp.getComponentName() + '%' + dstMethod;
        actionLinks.put(srcAction, dstAction);
        log.debug("Action " + srcAction + " links to " + dstAction);
    }

    public void doRender(Map parameters) throws PortletException {
        log.debug("doRender(" + compId + ")");
        if (activeComp == null) {
            throw new PortletException("No action component has not been set!");
        }
        // Render active action component if not already displayed
        if (!isDisplayed) {
            String compName = activeComp.getComponentName();
            String renderAction = compName + '%' + ActionComponent.RENDER_ACTION;
            if (performActionFilters(renderAction, ActionComponent.RENDER_ACTION, parameters)) {
                try {
                    activeComp.doRender(parameters);
                } catch (Exception e) {
                    log.error("Error rendering action component", e);
                    throw new PortletException("Unknown error rendering action component..." + e.getMessage());
                }
            }
            // Do next state
            doNextState();
        }
    }

    public void doAction(Class comp, String action, Map parameters) throws PortletException {
        //ActionComponentState compActionState = new ActionComponentState(comp, action, parameters);
        //ActionComponent component = getActionComponent(compActionState);
        nextActionState = new ActionComponentState(comp, action, parameters);
        ActionComponent component = getActionComponent(nextActionState);
        String compName = component.getComponentName();
        String compAction = compName + '%' + action;
        doAction(compAction, parameters);
    }

    public void doAction(String action, Map parameters) throws PortletException {
        log.debug("doAction(" + compId + "," + action + ")");
        int index = action.indexOf("%");
        if (index == -1)  {
            throw new PortletException("Invalid action " + action);
        } else if (activeComp == null) {
            throw new PortletException("No action component set yet");
        }
        // Get the action comp and methods...
        String compName = action.substring(0, index);
        String compMethod = action.substring(index+1);

        // Check if passes action filters
        if (performActionFilters(action, compMethod, parameters)) {
            // Check if this action links to another
            String actionLink = (String)actionLinks.get(action);
            if (actionLink != null) {
                log.debug("Linking action method " + actionLink);
                action = actionLink;
                index = action.indexOf("%");
                compName = action.substring(0, index);
                compMethod = action.substring(index+1);
            }
            // Get action comp with given name
            ActionComponent nextComp = (ActionComponent)actionComps.get(compName);
            if (nextComp == null) {
                throw new PortletException("No action component exists with name " + compName);
            }
            // Init if new action component, then load action component
            try {
                String nextCompId = nextComp.getComponentId();
                if (! activeComp.getComponentId().equals(nextCompId) ) {
                    log.debug("Invoking action method on new active component " + compName);
                    activeComp = nextComp;
                    activeCompId = nextCompId;
//                    activeComp.init((PortletRequest)request, response);
//                    activeComp.load((PortletRequest)request, response);
                    activeComp.init(portletRequest, portletResponse);
                    activeComp.load(portletRequest, portletResponse);
                }
            } catch (PortletException e) {
                log.error("Portlet error loading action component", e);
                throw e;
            } catch (Exception e) {
                log.error("Unknown error loading action component", e);
                throw new PortletException("Unknown error loading action component..." + e.getMessage());
            }
            // Do action compnent method
            try {
                activeComp.doAction(compMethod, parameters);
            } catch (PortletException e) {
                log.error("Portlet error loading action component", e);
                throw e;
            } catch (Exception e) {
                log.error("Unknown error rendering action component", e);
                throw new PortletException("Unknown error rendering action component..." + e.getMessage());
            }
        }
        // Do next state
        doNextState();
    }

    public boolean performActionFilters(String action, String method, Map parameters) {
        // If has action filters
        List actionFilterList = (List)actionFilterMap.get(action);
        boolean passedFilters = true;
        if (actionFilterList != null) {
            // Perform action filters
            try {
                for (Iterator actionFilters = actionFilterList.iterator(); actionFilters.hasNext();) {
                    ActionFilter filter =  (ActionFilter)actionFilters.next();
                    filter.filter(activeComp, method, parameters);
                }
            } catch (ActionFilterException e) {
                passedFilters = false;
                try {
                    activeComp.doRender(parameters);
                } catch (Exception e1) {
                    log.error("Error rendering action component", e1);
                }
            }
        }
        return passedFilters;
    }

    public ActionComponentState getNextState() {
        return nextActionState;
    }

    public void setNextState(ActionComponentState state) {
        log.debug("setNextState(" + compId + "," + state.getComponent().getName() + "," + state.getAction() + ")");
        nextActionState = state;
    }

    public void setNextState(Class actionClass) {
        setNextState(new ActionComponentState(actionClass));
    }

    public void setNextState(Class actionClass, String actionMethod) {
        setNextState(new ActionComponentState(actionClass, actionMethod));
    }

    public void setNextState(Class actionClass, String actionMethod, Map actionParams) {
        setNextState(new ActionComponentState(actionClass, actionMethod, actionParams));
    }

    public void doNextState() throws PortletException {
        if (nextActionState == null) {
            throw new PortletException("Next action component not provided");
        } else if (activeComp == null) {
            throw new PortletException("Action component not provided");
        } else {
            if (lastActionState != null && lastActionState.equals(nextActionState)) {
                log.debug("Last action state same as next action state for " + compId);
                String actionMethod = nextActionState.getAction();
                if (!actionMethod.endsWith(".jsp")) {
                    log.debug("Displaying default jsp page for component " + activeCompId);
                    displayPage(activeComp.getDefaultJspPage());
                }

            } else {

                Class nextActionClass = nextActionState.getComponent();
                String nextActionMethod = nextActionState.getAction();
                log.debug("doNextState(" + compId + "," + nextActionClass + "," + nextActionMethod + ")");
                if (! nextActionClass.equals(activeComp.getClass()) ) {
                    log.debug("Next active component changed to " + nextActionClass.getName());
                    //activeComp = getActionComponent(nextActionClass);
                    activeComp = getActionComponent(nextActionState);
                    activeCompId = activeComp.getComponentId();
//                    activeComp.init((PortletRequest)request, response);
//                    activeComp.load((PortletRequest)request, response);
                    activeComp.init(portletRequest, portletResponse);
                    activeComp.load(portletRequest, portletResponse);
                }
                if (nextActionMethod == null) {
                    log.debug("No action method provided, rendering action component");
                    doRender(nextActionState.getParameters());
                } else if (nextActionMethod.endsWith(".jsp")) {
                    displayPage(nextActionMethod);
                } else {
                    String compName = (String)actionCompNames.get(activeComp.getClass().getName());
                    nextActionMethod = compName + '%' + nextActionMethod;
                    log.debug("Invoking action method " + nextActionMethod);
                    doAction(nextActionMethod, nextActionState.getParameters());
                }
            }
        }
    }

    protected void displayPage(String page) throws PortletException {
        log.debug("Setting jsp page to " + activeComp.getServletContext().getServletContextName() + " " + page);
        isDisplayed = true;
        activeComp.store();
        setServletContext(activeComp.getServletContext());
        setPage(page);
    }

    public ActionDialog getActionDialog(ActionComponentState state)
            throws PortletException {
        return getActionDialog(state, this);
    }

    public ActionDialog getActionDialog(ActionComponentState state,
                                        ActionComponentFrame baseContainer)
            throws PortletException {
        // Parent here...
        if (parent == null) {
            Class compClass = state.getComponent();
            ActionDialog comp = null;
            log.debug("Getting action dialog " + compId + "%" + compClass.getName());
            String compName = (String)actionCompNames.get(compClass.getName());
            if (compName == null) {
                ++numberOfComponents;
                compName = String.valueOf(numberOfComponents);
                actionCompNames.put(compClass.getName(), compName);
                comp = (ActionDialog)createActionComponent(state, compName);
                actionComps.put(compName, comp);
            } else {
                comp = (ActionDialog)actionComps.get(compName);
                if (comp == null) {
                    throw new PortletException("No action component exists named " + compName);
                }
                if (! comp.getComponentId().equals(activeComp.getComponentId()) ) {
                    comp = (ActionDialog)createActionComponent(state, compName);
                    actionComps.put(compName, comp);
                }
            }
            comp.setBaseContainer(baseContainer);
            log.debug("Returning comp " + comp.getComponentId());
            return comp;
        } else {
            ActionComponentFrame container = parent.getContainer();
            log.debug("Returning parent container get action dialog...");
            return container.getActionDialog(state, baseContainer);
        }
    }

    public ActionComponent getRootComponent() throws PortletException {
        // Parent here...
        if (parent == null) {
            return getActiveComponent();
        }
        return parent.getContainer().getRootComponent();
    }

    public ActionComponent getActionComponent(Class compClass)
            throws PortletException {
        return getActionComponent(new ActionComponentState(compClass));
    }

    public ActionComponent getActionComponent(Class compClass, Map parameters)
            throws PortletException {
        return getActionComponent(new ActionComponentState(compClass, parameters));
    }

    public ActionComponent getActionComponent(ActionComponentState state)
            throws PortletException {
        Class compClass = state.getComponent();
        ActionComponent comp = null;
        log.debug("Getting action component " + compId + "%" + compClass.getName());
        String compName = (String)actionCompNames.get(compClass.getName());
        if (compName == null) {
            ++numberOfComponents;
            compName = String.valueOf(numberOfComponents);
            actionCompNames.put(compClass.getName(), compName);
            comp = createActionComponent(state, compName);
            actionComps.put(compName, comp);
        } else {
            comp = (ActionComponent)actionComps.get(compName);
            log.debug("Returning action component " + compId + "%" + compClass.getName());
            if (comp == null) {
                throw new PortletException("No action component exists named " + compName);
            }
        }
        return comp;
    }

    public ActionComponent createActionComponent(ActionComponentState state,
                                                 String compName)
            throws PortletException {
        Class compClass = state.getComponent();
        log.debug("Creating action component " + compId + "%" + compClass.getName());
        log.debug("Creating action comp " + compClass.getName());
        PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
        try {
        ActionComponentFactory compFactory  = (ActionComponentFactory)
                serviceFactory.createPortletService(ActionComponentFactory.class, null, true);
        return compFactory.createActionComponent(this, compClass, compName);
        } catch (PortletServiceException e) {
            log.error("Error creating action component factory", e);
            throw new PortletException(e);
        }
    }

    public Object getAttribute(String name) {
//        return request.getAttribute(compPrefix + name);
        return portletRequest.getAttribute(compPrefix + name);
    }

    public void setAttribute(String name, Object value) {
//        request.setAttribute(compPrefix + name, value);
        portletRequest.setAttribute(compPrefix + name, value);
    }

    public void addActionFilter(ActionComponent component, List methodList, ActionFilter filter) {
        String actionCompName = component.getComponentName();
        for (Iterator methods = methodList.iterator(); methods.hasNext();) {
            String method = (String) methods.next();
            String action = actionCompName + '%' + method;
            List actionFilters = (List)actionFilterMap.get(action);
            if (actionFilters == null) {
                actionFilters = new ArrayList();
                actionFilterMap.put(action, actionFilters);
            }
            actionFilters.add(filter);
        }
    }

    public void addActionFilter(ActionComponent component, String methods[], ActionFilter filter) {
        String actionCompName = component.getComponentName();
        for (int ii = 0; ii < methods.length; ++ii) {
            String action = actionCompName + '%' + methods[ii];
            List actionFilters = (List)actionFilterMap.get(action);
            if (actionFilters == null) {
                actionFilters = new ArrayList();
                actionFilterMap.put(action, actionFilters);
            }
            actionFilters.add(filter);
        }
    }

    public void addActionFilter(ActionComponent component, String method, ActionFilter filter) {
        String actionCompName = component.getComponentName();
        String action = actionCompName + '%' + method;
        List actionFilters = (List)actionFilterMap.get(action);
        if (actionFilters == null) {
            actionFilters = new ArrayList();
            actionFilterMap.put(action, actionFilters);
        }
        actionFilters.add(filter);
    }
}
