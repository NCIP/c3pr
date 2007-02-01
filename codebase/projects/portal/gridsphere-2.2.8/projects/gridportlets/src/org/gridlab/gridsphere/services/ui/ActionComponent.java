/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionComponent.java,v 1.1.1.1 2007-02-01 20:41:56 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;

import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.services.security.gss.CredentialManagerService;
import org.gridlab.gridsphere.services.util.GridSphereUserUtil;
import org.gridlab.gridsphere.services.util.GridPortletsResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletException;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;
import java.util.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.io.File;


/**
 * Action components implement an event / component based model for developing portlets.
 */
public abstract class ActionComponent {

    private transient static PortletLog log = SportletLog.getInstance(ActionComponent.class);

    public static final String DEFAULT_ACTION = "+default+";
    public static final String UNDEFINED_ACTION = "+undefined+";
    public static final String RENDER_ACTION = "+render+";
    public static final String ROOT_COMPONENT = "+root+";
    public static final String PARENT_COMPONENT = "+parent+";

    public static final String MESSAGE_BOX_ID = "messageBox";
    public static final String MESSAGE_BOX_KEY_PARAM = "messageBoxTextParam";
    public static final String MESSAGE_BOX_TEXT_PARAM = "messageBoxTextParam";
    public static final String MESSAGE_BOX_TYPE_PARAM = "messageBoxTypeParam";

    public static final String MESSAGE_CRED_REQ = "portlets.security.gss.message_credential_required";
    public static final String MESSAGE_ERR_REND = "portlets.message_error_rendering";

    protected ServletContext servletContext = null;
    protected ActionComponentFrame container = null;
    protected ActionComponentFrame root = null;

    protected String compId = null;
    protected String compName = null;
    protected String compPrefix = null;

    protected PortletRequest portletRequest = null;
    protected PortletResponse portletResponse = null;
    protected Locale locale = null;

    protected Hashtable actionComponentBeans = new Hashtable();
    protected Hashtable tagBeans = new Hashtable();

    protected String lastAction = null;
    protected String defaultAction = UNDEFINED_ACTION;
    protected String renderAction = null;

    protected String lastJspPage = null;
    protected String defaultJspPage = null;

    private HashMap attributes = new HashMap();

    protected MessageBoxBean messageBox = null;

    protected CredentialManagerService credentialManagerService = null;

    protected String resourceBundleBaseName = GridPortletsResourceBundle.BASE_NAME;

    protected boolean containsForms = false;

    private ActionComponentEvent actionComponentEvent = null;

    protected ActionComponent() {
    }

    public ActionComponent(ActionComponentFrame container, String compName) {
        log.debug("ActionComponent(" + compName + ")");
        this.container = container;
        this.compName = compName;
        compId = container.getComponentId() + '%' + compName;
        compPrefix = compId + '%';
        // Init message box
        messageBox = createMessageBoxBean(MESSAGE_BOX_ID);
        actionComponentEvent = new ActionComponentEvent(this);
    }

    public ActionComponentFrame getContainer() {
        return container;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext context) {
        servletContext = context;
    }

    public boolean containsForms() {
        return containsForms;
    }

    protected void setContainsForms(boolean flag) {
        containsForms = flag;
    }

    public Locale getLocale() {
        return locale;
    }

    public User getUser() {
        return getGridSphereUser(portletRequest);
    }

    public MessageBoxBean getMessageBox() {
        return messageBox;
    }

    public Map getTagBeans() {
        return tagBeans;
    }

    public String getResourceBundleBaseName() {
        return resourceBundleBaseName;
    }

    protected void setResourceBundleBaseName(String baseName) {
        resourceBundleBaseName = baseName;
    }

    public String getResourceString(String key) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(resourceBundleBaseName, locale);
            return bundle.getString(key);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return key;
        }
    }

    public String getResourceString(String key, String defaultValue) {
        String value = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(resourceBundleBaseName, locale);
            value = bundle.getString(key);
            if (value == null) {
                value = defaultValue;
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return value;
    }

    public String getResourceString(Locale locale, String key) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(resourceBundleBaseName, locale);
            return bundle.getString(key);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return key;
        }
    }


    public String getResourceString(Locale locale, String key, String defaultValue) {
        String value = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(resourceBundleBaseName, locale);
            value = bundle.getString(key);
            if (value == null) {
                value = defaultValue;
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return value;
    }

    public void init(PortletRequest request, PortletResponse response) throws PortletException {

        log.debug("ActionComponent.init(" + compId + ") request params " + request.getParameterMap().size());

        log.debug("init(" + compId + ")");
        portletRequest = request;
        portletResponse = response;
        locale = request.getLocale();
        // Init action component beans
        initActionComponentBeans(request, response);
        // Invoke onInit event
        onInit();
    }

    protected void initActionComponentBeans(PortletRequest request, PortletResponse response) throws PortletException {
        log.debug("Initing " + actionComponentBeans.size() + " action component beans");
        Enumeration beans = actionComponentBeans.elements();
        while (beans.hasMoreElements()) {
            ActionComponentFrame bean = (ActionComponentFrame)beans.nextElement();
            bean.init(request, response);
        }
    }

    /**
     * Initializes instances of all appropriate
     * portlet, form and service objects.
     */
    public void load(PortletRequest request, PortletResponse response) throws PortletException {

        log.debug("ActionComponent.load(" + compId + ") request params " + request.getParameterMap().size());

        portletRequest = request;
        portletResponse = response;
        locale = request.getLocale();
        // Init tag beans
        initTagBeans(request);
        // Load each tag bean from request
        log.debug("Loading " + tagBeans.size() + " tag beans");
        actionComponentEvent.process(portletRequest, portletResponse);
        // Clear message box
        messageBox.clearMessage();
        messageBox.setMessageType(TextBean.MSG_INFO);
        // Load action component beans
        loadActionComponentBeans(portletRequest, portletResponse);
        // Invoke onLoad event
        onLoad();
    }

    protected void initTagBeans(PortletRequest request) throws PortletException {
        log.debug("Resetting " + tagBeans.size() + " tag beans");
        Iterator it = tagBeans.values().iterator();
        while (it.hasNext()) {
            BaseBean tagBean = (BaseBean) it.next();
            log.debug("Initing tag bean " + tagBean.getBeanId());
            //tagBean.setPortletRequest(request);
        }
    }

    protected void loadActionComponentBeans(PortletRequest request, PortletResponse response) throws PortletException {
        log.debug("Initing " + actionComponentBeans.size() + " action component beans");
        Enumeration beans = actionComponentBeans.elements();
        while (beans.hasMoreElements()) {
            ActionComponentFrame bean = (ActionComponentFrame)beans.nextElement();
            bean.load(request, response);
        }
    }

    public void store() throws PortletException {
        log.debug("store(" + compId + ")");

        // Handle on store event
        onStore();

        // Set comp id in portlet request to this comp id
        String origCompId = (String)portletRequest.getAttribute(SportletProperties.GP_COMPONENT_ID);
        portletRequest.setAttribute(SportletProperties.GP_COMPONENT_ID, compId);
        log.debug("Changed component id from " + origCompId + " to " + compId);

        actionComponentEvent.store();

        portletRequest.setAttribute(SportletProperties.GP_COMPONENT_ID, origCompId);
        log.debug("Changed component id from " + compId + " back to " + origCompId);

    }

    public String getComponentId() {
        return compId;
    }

    public String getComponentName() {
        return compName;
    }

    public String getDefaultAction() {
        return defaultAction;
    }

    public void setDefaultAction(String methodName) {
        defaultAction = methodName;
    }

    public String getRenderAction() {
        return renderAction;
    }

    public void setRenderAction(String methodName) {
        renderAction = methodName;
    }

    public String getDefaultJspPage() {
        return defaultJspPage;
    }

    public void setDefaultJspPage(String methodName) {
        defaultJspPage = methodName;
    }

    public void doRender(Map parameters) throws PortletException {

        log.debug("doRender for (" + compId + ")");
        if (lastAction == null) {
            doMethod(defaultAction, new HashMap());
        } else if (renderAction != null) {
            log.debug("Calling render action " + renderAction + " for (" + compId + ")");
            doMethod(renderAction, new HashMap());
        } else if (lastJspPage == null) {
            if (defaultJspPage == null) {
                throw new PortletException("No jsp page to render!");
            } else {
                setNextState(defaultJspPage);
            }
        } else {
            setNextState(lastJspPage);
        }
        // Render child components
        renderActionComponentBeans("", parameters);
    }

    public void doAction(String methodName, Map parameters)
            throws PortletException {
        log.debug("doAction(" + compId + "," + methodName + ")");
        // Now perform method...
        String compName = null;
        int index = methodName.indexOf("%");
        if (index == -1)  {
            if (methodName.equals(RENDER_ACTION)) {
                doRender(parameters);
            } else if (methodName.startsWith(PARENT_COMPONENT)) {
                // Do action on parent component
                methodName = methodName.substring(PARENT_COMPONENT.length());
                ActionComponent parent = container.getParentComponent();
                if (parent == null) {
                    doMethod(methodName, parameters);
                } else {
                    parent.doAction(methodName, parameters);
                }
            } else if (methodName.startsWith(ROOT_COMPONENT)) {
                // Do action on parent component
                methodName = methodName.substring(ROOT_COMPONENT.length());
                ActionComponent root = container.getRootComponent();
                if (root == null || root.getComponentId().equals(compId)) {
                    doMethod(methodName, parameters);
                } else {
                    root.doAction(methodName, parameters);
                }
            } else {
                doMethod(methodName, parameters);
            }
            compName = "";
        } else {
            // Get comp name and method
            compName = methodName.substring(0, index);
            String compMethod = methodName.substring(index+1);

            log.debug("Performing component " + compName + " action method " + compMethod);
            ActionComponentFrame bean = (ActionComponentFrame)actionComponentBeans.get(compName);
            if (bean == null) {
                throw new PortletException("No action component bean exists with name " + compName);
            }
            try {
                bean.doAction(compMethod, parameters);
                bean.store();
            } catch (Exception e) {
                String msg = "Unable to perform action method " + methodName;
                log.error(msg, e);
            }
        }

        // Render child components
        renderActionComponentBeans(compName, parameters);
    }

    public void doMethod(String methodName, Map parameters)
            throws PortletException {
        log.debug("doMethod(" + compId + "," + methodName + ")");
        if (methodName == null || methodName.equals(DEFAULT_ACTION)) {
            log.debug("Using default action");
            methodName = defaultAction;
            log.debug("Invoking default action method " + methodName);
        }
        // Get object and class references
        Object thisObject = (Object)this;
        Class thisClass = getClass();
        Class[] parameterTypes = new Class[] { Map.class };
        Object[] arguments = new Object[] { parameters };
        // Call method specified by action name
        try {
            log.debug("Getting action method " + thisClass.getName() + "%" + methodName + "()");
            Method method = thisClass.getMethod(methodName, parameterTypes);
            log.debug("Invoking action method " + thisClass.getName() + "%" + methodName + "()");
            method.invoke(thisObject, arguments);
        } catch (NoSuchMethodException e) {
            String error = "No such method: " + methodName + '\n' + e.getMessage();
            log.error(error, e);
            // If action is not illegal do error undefined action
            doError(e);
        } catch (IllegalAccessException e) {
            String error = "Error accessing action method: " + methodName + '\n' + e.getMessage();
            log.error(error, e);
            // If action is not illegal do error undefined action
            doError(e);
        } catch (InvocationTargetException e) {
            String error = "Error invoking action method: " + methodName + '\n' + e.getMessage();
            log.error(error, e);
            // If action is not illegal do error undefined action
            doError(e);
        }
        lastAction = methodName;
    }

    public void doTest(Map parameters) throws PortletException {
        log.debug("doTest(" + compId + ")");
        messageBox.appendText("Testing this component..." + compId);
    }

    public void doError(Throwable e)
            throws PortletException {
        log.error("Error rendering component " + compId + " with class " + getClass().getName());
        messageBox.appendText(getResourceString(MESSAGE_ERR_REND));
        messageBox.setMessageType(TextBean.MSG_ERROR);
        setNextState(defaultJspPage);
    }

    protected void renderActionComponentBeans(String exceptComp, Map parameters) {
        // Render child components
        log.debug("Rendering child " + actionComponentBeans.size() + " components");
        Enumeration e = actionComponentBeans.keys();
        while (e.hasMoreElements()) {
            String nextCompName = (String)e.nextElement();
            if (!nextCompName.equals(exceptComp)) {
                renderActionComponentBean(nextCompName, parameters);
            }
        }
    }

    protected void renderActionComponentBean(String nextCompName, Map parameters) {

        try {
            ActionComponentFrame bean = (ActionComponentFrame)actionComponentBeans.get(nextCompName);
            bean.doRender(parameters);
            bean.store();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    public CredentialManagerService getCredentialManagerService() throws PortletException {
        if (credentialManagerService == null) {
            credentialManagerService = (CredentialManagerService)
                 getPortletService(CredentialManagerService.class);
        }
        return credentialManagerService;
    }

    public void displayCredentialsRequired(String method) {
        messageBox.appendText(getResourceString(MESSAGE_CRED_REQ));
        messageBox.setMessageType(TextBean.MSG_ERROR);
    }

    public static PortletService getPortletService(Class portletService)
            throws PortletException {
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        try {
            return factory.createPortletService(portletService, null, true);
        } catch (PortletServiceException e) {
            log.error("Unaable to create portlet service", e);
            throw new PortletException(e);
        }
    }

    protected void setNextState(Class actionClass) {
        container.setNextState(actionClass, null);
    }

    protected void setNextState(String action) {
        setNextState(new ActionComponentState(getClass(), action));
    }

    protected void setNextState(Class actionClass, String action) {
        setNextState(new ActionComponentState(actionClass, action));
    }

    protected void setNextState(Class actionClass, String action, Map parameters) {
        setNextState(new ActionComponentState(actionClass, action, parameters));
    }

    protected void setNextState(ActionComponentState nextState) {
        Class actionClass = nextState.getComponent();
        String action = nextState.getAction();
        if (actionClass != null && actionClass.equals(getClass())) {
            if (action != null && action.endsWith(".jsp")) {
                lastJspPage = action;
            }
        }
        container.setNextState(nextState);
    }

    protected void onInit() throws PortletException {
        log.debug("onInit(" + compId + ")");
        //messageBox.clearMessage();
        //messageBox.appendText("Initing... " + compId);
    }

    protected void onLoad() throws PortletException {
        log.debug("onLoad(" + compId + ")");
        //messageBox.appendText("Loading... " + compId);
    }

    protected void onStore() throws PortletException {
        log.debug("onStore(" + compId + ")");
        //messageBox.appendText("Storing... " + compId);
    }

    /**
     * Creates an action dialog of the given class
     * and associates it with the given component widget.
     * @return An action dialog
     */
    public ActionDialog getActionDialog(Class dialogClass)
            throws PortletException {
        ActionComponentState dialogState =  new ActionComponentState(dialogClass);
        return container.getActionDialog(dialogState);
    }

    /**
     * Creates an action component of the given class
     * and associates it with the given component widget.
     * @return An action component
     */
    public static ActionComponent getActionComponent(ActionComponentFrame compFrame, Class compClass)
            throws PortletException {
        ActionComponentState compState =  new ActionComponentState(compClass);
        return compFrame.getActionComponent(compState);
    }

    /**
     * Creates an action component frame with the given bean id.
     * @param beanId The bean id of the action component frame
     * @return An action component frame
     */
    public ActionComponentFrame createActionComponentFrame(String beanId) {
        ActionComponentFrame bean = new ActionComponentFrame(this, beanId);
        actionComponentBeans.put(beanId, bean);
        return bean;
    }

    /**
     * Return an existing <code>ActionSubmitBean</code> or create a new one
     * @param beanId The bean id
     * @return An action submit bean
     */
    public ActionSubmitBean createActionSubmitBean(String beanId) {
        ActionSubmitBean as = new ActionSubmitBean(beanId);
        tagBeans.put(beanId, as);
        return as;
    }

    /**
     * Return an existing <code>ActionLinkBean</code> or create a new one
     * @param beanId The bean id
     * @return an action link bean
     */
    public ActionLinkBean createActionLinkBean(String beanId) {
        ActionLinkBean al = new ActionLinkBean(beanId);
        tagBeans.put(beanId, al);
        return al;
    }

    /**
     * Return an existing <code>ActionParamBean</code> or create a new one
     *
     * @param beanId The bean id
     * @return An action parameter bean
     */
    public ActionParamBean createActionParamBean(String beanId) {
        ActionParamBean ap = new ActionParamBean(beanId);
        tagBeans.put(beanId, ap);
        return ap;
    }

    /**
     * Return an existing <code>HiddenFieldBean</code> or create a new one
     * @param beanId The bean id
     * @return A hidden field bean
     */
    public HiddenFieldBean createHiddenFieldBean(String beanId) {
        HiddenFieldBean hf = new HiddenFieldBean(beanId);
        tagBeans.put(beanId, hf);
        return hf;
    }

    /**
     * Return an existing <code>TextFieldBean</code> or create a new one
     * @param beanId The bean id
     * @return A text field bean
     */
    public TextFieldBean createTextFieldBean(String beanId) {
        TextFieldBean tf = new TextFieldBean(beanId);
        tagBeans.put(beanId, tf);
        return tf;
    }

    /**
     * Return an existing <code>TextAreaBean</code> or create a new one
     * @param beanId The bean id
     * @return A text area bean
     */
    public TextAreaBean createTextAreaBean(String beanId) {
        TextAreaBean ta = new TextAreaBean(beanId);
        tagBeans.put(beanId, ta);
        return ta;
    }

    /**
     * Return an existing <code>PasswordBean</code> or create a new one
     * @param beanId The bean id
     * @return A password bean
     */
    public PasswordBean createPasswordBean(String beanId) {
        PasswordBean pb = new PasswordBean(beanId);
        tagBeans.put(beanId, pb);
        return pb;
    }

    /**
     * Return an existing <code>CheckBoxBean</code> or create a new one
     * @param beanId The bean id
     * @return A check box bean
     */
    public CheckBoxBean createCheckBoxBean(String beanId) {
        CheckBoxBean cb = new CheckBoxBean(beanId);
        tagBeans.put(beanId, cb);
        return cb;
    }

    /**
     * Return an existing <code>RadioButtonBean</code> or create a new one
     * @param beanId The bean id
     * @return A radio button bean
     */
    public RadioButtonBean createRadioButtonBean(String beanId) {
        MyRadioButtonBean rb = new MyRadioButtonBean(beanId);
        tagBeans.put(beanId, rb);
        return rb;
    }

    /**
     * Return an existing <code>FileInputBean</code> or create a new one
     * @param beanId The bean id
     * @return A file input bean
     */
    public FileInputBean createFileInputBean(String beanId) {
        try {
            FileInputBean fi = new FileInputBean(beanId);
            tagBeans.put(beanId, fi);
            return fi;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public void setFileDownloadEvent(String fileName, String path) {
        portletRequest.getPortletSession(true).setAttribute(SportletProperties.FILE_DOWNLOAD_NAME, fileName);
        portletRequest.getPortletSession(true).setAttribute(SportletProperties.FILE_DOWNLOAD_PATH, path);
        portletRequest.getPortletSession(true).setAttribute(SportletProperties.FILE_DELETE, Boolean.valueOf(true));
//        portletRequest.setAttribute(SportletProperties.FILE_DOWNLOAD_NAME, fileName);
//        portletRequest.setAttribute(SportletProperties.FILE_DOWNLOAD_PATH, path);
//        portletRequest.setAttribute(SportletProperties.FILE_DELETE, Boolean.valueOf(true));
    }

    protected static String getUserName(PortletRequest request) {
        Map userInfo = (Map) request.getAttribute(PortletRequest.USER_INFO);
        return (String)userInfo.get("user.name");
    }

    protected static User getGridSphereUser(PortletRequest request) {
        User user = (User)request.getAttribute("gridsphere.user");
        if (user == null) {
            Map userInfo = (Map) request.getAttribute(PortletRequest.USER_INFO);
            String userName = (String)userInfo.get("user.name");
            // TODO: Temporary... to make work with Jetspeed
            if (userName == null) userName = "root";
            user = GridSphereUserUtil.getUserByUserName(userName);
            request.setAttribute("gridsphere.user", user);
        }
        return user;
    }

    /**
     * Return an existing <code>TextBean</code> or create a new one
     * @param beanId The bean id
     * @return A text bean
     */
    public TextBean createTextBean(String beanId) {
        TextBean tb = new TextBean(beanId);
        tagBeans.put(beanId, tb);
        return tb;
    }

    /**
     * Return an existing <code>ImageBean</code> or create a new one
     * @param beanId The bean id
     * @return An image bean
     */
    public ImageBean createImageBean(String beanId) {
//        ImageBean ib = new ImageBean(null, beanId);
        ImageBean ib = new ImageBean();
        ib.setBeanId(beanId);
        tagBeans.put(beanId, ib);
        return ib;

    }

    /**
     * Return an existing <code>ListBoxBean</code> or create a new one
     * @param beanId The bean id
     * @return A list box bean
     */
    public ListBoxBean createListBoxBean(String beanId) {
        ListBoxBean lb = new ListBoxBean(beanId);
        tagBeans.put(beanId, lb);
        return lb;
    }

    /**
     * Return an existing <code>ListBoxItemBean</code> or create a new one
     * @param beanId The bean id
     * @return A list box item bean
     */
    public ListBoxItemBean createListBoxItemBean(String beanId) {
        ListBoxItemBean lb = new ListBoxItemBean(beanId);
        tagBeans.put(beanId, lb);
        return lb;
    }

    /**
     * Return an existing <code>ActionMenuBean</code> or create a new one
     * @param beanId The bean id
     * @return An action menu bean
     */
    public ActionMenuBean createActionMenuBean(String beanId) {
        ActionMenuBean lb = new ActionMenuBean(beanId);
        tagBeans.put(beanId, lb);
        return lb;
    }


    public ActionMenuItemBean createActionMenuItemBean(String beanId) {
        ActionMenuItemBean lb = new ActionMenuItemBean(beanId);
        tagBeans.put(beanId, lb);
        return lb;
    }


    /**
     * Return an existing <code>MessageBoxBean</code> or create a new one
     * @param beanId The bean id
     * @return A message box bean
     */
    public MessageBoxBean createMessageBoxBean(String beanId) {
        MessageBoxBean messageBoxBean = new MessageBoxBean(beanId);
        tagBeans.put(beanId, messageBoxBean);
        return messageBoxBean;
    }

    /**
     * Returns the attribute with the given name for this action component.
     * @param name The page attribute name
     * @return The page attribute object
     */
    public Object getPageAttribute(String name) {
        if (portletRequest == null) return null;
        return portletRequest.getAttribute(compPrefix + name);
    }

    /**
     * Returns the attribute with the given name for this action component.
     * @param name the page attribute nae
     * @param defaultValue The default value if attribute does not exist
     * @return The page attribute object
     */
    public Object getPageAttribute(String name, Object defaultValue) {
        if (portletRequest == null) return null;
        Object value = portletRequest.getAttribute(compPrefix + name);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }
    /**
     * Sets the attribute with the given name and value for this component.
     * @param name The page attribute name
     * @param value The page attribute object
     */
    public void setPageAttribute(String name, Object value) {
        if (portletRequest == null) return;
        portletRequest.setAttribute(compPrefix + name, value);
    }

    /**
     * Returns the given attribute name from the request for the active
     * component. For use in jsps...
     * @param request The request object available in JSP
     * @param name The page attribute name
     * @return The page attribute object
     */
    public static Object getPageAttribute(HttpServletRequest request, String name) {
        String compId = (String) request.getAttribute(SportletProperties.GP_COMPONENT_ID);
        return request.getAttribute(compId + '%' + name);
    }

    /**
     * Returns the given attribute name from the request for the active
     * component. For use in jsps...
     * @param request The request object available in JSP
     * @param name The page attribute name
     * @param defaultValue The default value if attribute does not exist
     * @return The page attribute object
     */
    public static Object getPageAttribute(HttpServletRequest request, String name, Object defaultValue) {
        String compId = (String) request.getAttribute(SportletProperties.GP_COMPONENT_ID);
        Object value = request.getAttribute(compId + '%' + name);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    public Map getAttributes() {
        return attributes;
    }

    public Object getAttribute(String name, Object defaultValue) {
        Object value = attributes.get(name);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    public void setAttributes(HashMap attributes) {
        this.attributes = attributes;
    }

    public boolean equals(Object object){
        if ( this == object ) return true;
        if ( !(object instanceof ActionComponent) ) return false;
        ActionComponent comp = (ActionComponent)object;
        return (comp.getComponentId().equals(compId) && comp.getClass().equals(comp.getClass()));
    }

    public boolean equals(ActionComponent comp){
        return (comp.getComponentId().equals(compId) && comp.getClass().equals(comp.getClass()));
    }
}
