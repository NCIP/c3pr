/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 *
 * @version $Id: BaseFormEventImpl.java,v 1.1.1.1 2007-02-01 20:50:59 kherm Exp $
 */
package org.gridlab.gridsphere.provider.event.impl;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.provider.portletui.beans.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.portlet.PortletResponse;
import javax.portlet.PortletRequest;
import java.io.IOException;
import java.util.*;

/**
 * The <code>FormEventImpl</code> provides methods for creating/retrieving visual beans
 * from the <code>PortletRequest</code>
 */
public abstract class BaseFormEventImpl {

    protected transient static PortletLog log = SportletLog.getInstance(BaseFormEventImpl.class);

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected PortletRequest portletRequest;
    protected PortletResponse portletResponse;
    protected Locale locale = null;

    protected Map tagBeans = null;
    protected List fileItems = null;

    protected String cid = null;
    protected String compId = null;

    protected BaseFormEventImpl() {

    }

    public BaseFormEventImpl(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        locale = (Locale) request.getSession(true).getAttribute(SportletProperties.LOCALE);
        if (locale == null) locale = request.getLocale();
        if (locale == null) locale = Locale.ENGLISH;
        cid = (String)request.getAttribute(SportletProperties.COMPONENT_ID);
        compId = (String)request.getAttribute(SportletProperties.GP_COMPONENT_ID);
    }

    public BaseFormEventImpl(PortletRequest request, PortletResponse response) {
        this.portletRequest = request;
        this.portletResponse = response;
        locale = (Locale) portletRequest.getPortletSession(true).getAttribute(SportletProperties.LOCALE);
        if (locale == null) locale = portletRequest.getLocale();
        if (locale == null) locale = Locale.ENGLISH;
        cid = (String)portletRequest.getAttribute(SportletProperties.COMPONENT_ID);
        compId = (String)portletRequest.getAttribute(SportletProperties.GP_COMPONENT_ID);
    }

    /**
     * Returns the collection of visual tag beans contained by this form event
     *
     * @return the collection of visual tag beans
     */
    public Map getTagBeans() {
        return tagBeans;
    }

    public Object getRequest() {
        if (request != null) return request;
        if (portletRequest != null) return portletRequest;
        return null;
    }

    protected void configureBean(TagBean tagBean) {
        tagBean.setLocale(locale);
        if (cid != null) tagBean.addParam(SportletProperties.COMPONENT_ID, cid);
        if (compId != null) tagBean.addParam(SportletProperties.GP_COMPONENT_ID, compId);
    }

    /**
     * Return an existing <code>ActionLinkBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a ActionLinkBean
     */
    public ActionLinkBean getActionLinkBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (ActionLinkBean) tagBeans.get(beanKey);
        }
        ActionLinkBean al = new ActionLinkBean(beanId);
        configureBean(al);
        tagBeans.put(beanKey, al);
        return al;
    }

    /**
     * Return an existing <code>ActionParamBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a ActionParamBean
     */
    public ActionParamBean getActionParamBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (ActionParamBean) tagBeans.get(beanKey);
        }
        ActionParamBean ap = new ActionParamBean(beanId);
        configureBean(ap);
        tagBeans.put(beanKey, ap);
        return ap;
    }

    /**
     * Return an existing <code>ActionSubmitBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a ActionSubmitBean
     */
    public ActionSubmitBean getActionSubmitBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (ActionSubmitBean) tagBeans.get(beanKey);
        }
        ActionSubmitBean as = new ActionSubmitBean(beanId);
        configureBean(as);
        tagBeans.put(beanKey, as);
        return as;
    }

    /**
     * Return an existing <code>CheckBoxBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a CheckBoxBean
     */
    public CheckBoxBean getCheckBoxBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (CheckBoxBean) tagBeans.get(beanKey);
        }
        CheckBoxBean cb = new CheckBoxBean(beanId);
        configureBean(cb);
        tagBeans.put(beanKey, cb);
        return cb;
    }

    /**
     * Return an existing <code>CalendarBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a CalendarBean
     */
    public CalendarBean getCalendarBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (CalendarBean) tagBeans.get(beanKey);
        }
        CalendarBean ca = new CalendarBean(beanId);
        configureBean(ca);
        tagBeans.put(beanKey, ca);
        return ca;
    }

    /**
     * Return an existing <code>RadioButtonBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a RadioButtonBean
     */
    public RadioButtonBean getRadioButtonBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (RadioButtonBean) tagBeans.get(beanKey);
        }
        RadioButtonBean rb = new RadioButtonBean(beanId);
        configureBean(rb);
        tagBeans.put(beanKey, rb);
        return rb;
    }

    /**
     * Return an existing <code>PanelBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a PanelBean
     */
    public PanelBean getPanelBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (PanelBean) tagBeans.get(beanKey);
        }
        PanelBean pb = new PanelBean(beanId);
        configureBean(pb);
        tagBeans.put(beanKey, pb);
        return pb;
    }

    /**
     * Return an existing <code>TextFieldBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a TextFieldBean
     */
    public TextFieldBean getTextFieldBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        //log.debug("Checking for textfieldbean with bean key=" + beanKey);
        if (tagBeans.containsKey(beanKey)) {
            return (TextFieldBean) tagBeans.get(beanKey);
        }
        TextFieldBean tf = new TextFieldBean(beanId);
        configureBean(tf);
        tagBeans.put(beanKey, tf);
        return tf;
    }

    /**
     * Return an existing <code>TextEditorBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a TextEditorBean
     */
    public TextEditorBean getTextEditorBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        //log.debug("Checking for texteditorbean with bean key=" + beanKey);
        if (tagBeans.containsKey(beanKey)) {
            return (TextEditorBean) tagBeans.get(beanKey);
        }
        TextEditorBean te = new TextEditorBean(beanId);
        configureBean(te);
        tagBeans.put(beanKey, te);
        return te;
    }

    /**
     * Return an existing <code>HiddenFieldBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a HiddenFieldBean
     */
    public HiddenFieldBean getHiddenFieldBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (HiddenFieldBean) tagBeans.get(beanKey);
        }
        HiddenFieldBean hf = new HiddenFieldBean(beanId);
        configureBean(hf);
        tagBeans.put(beanKey, hf);
        return hf;
    }

    /**
     * Return an existing <code>FileInputBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a FileInputBean
     */
    public FileInputBean getFileInputBean(String beanId) throws IOException {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (FileInputBean) tagBeans.get(beanKey);
        }
        FileInputBean fi = new FileInputBean(beanId);
        configureBean(fi);
        tagBeans.put(beanKey, fi);
        return fi;
    }

    /**
     * Return an existing <code>PasswordBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a PasswordBean
     */
    public PasswordBean getPasswordBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (PasswordBean) tagBeans.get(beanKey);
        }
        PasswordBean pb = new PasswordBean(beanId);
        configureBean(pb);
        tagBeans.put(beanKey, pb);
        return pb;
    }

    /**
     * Return an existing <code>TextAreaBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a TextAreaBean
     */
    public TextAreaBean getTextAreaBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (TextAreaBean) tagBeans.get(beanKey);
        }
        TextAreaBean ta = new TextAreaBean(beanId);
        configureBean(ta);
        tagBeans.put(beanKey, ta);
        return ta;
    }

    /**
     * Return an existing <code>FrameBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a FrameBean
     */
    public FrameBean getFrameBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (FrameBean) tagBeans.get(beanKey);
        }
        FrameBean fb = new FrameBean(beanId);
        configureBean(fb);
        //System.err.println("Creating new frame bean" + beanId + " bean key= " + beanKey);
        tagBeans.put(beanKey, fb);
        return fb;
    }

    /**
     * Return an existing <code>TextBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a TextBean
     */
    public TextBean getTextBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        //log.debug("Checking for textbean with bean key=" + beanKey);
        if (tagBeans.containsKey(beanKey)) {
            return (TextBean) tagBeans.get(beanKey);
        }
        TextBean tb = new TextBean(beanId);
        configureBean(tb);
        tagBeans.put(beanKey, tb);
        return tb;
    }

    /**
     * Return an existing <code>ImageBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a ImageBean
     */
    public ImageBean getImageBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (ImageBean) tagBeans.get(beanKey);
        }
        ImageBean ib = new ImageBean(beanId);
        configureBean(ib);
        tagBeans.put(beanKey, ib);
        return ib;

    }

    /**
     * Return an existing <code>IncludeBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a IncludeBean
     */
    public IncludeBean getIncludeBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (IncludeBean) tagBeans.get(beanKey);
        }
        IncludeBean includeBean = new IncludeBean(beanId);
        configureBean(includeBean);
        tagBeans.put(beanKey, includeBean);
        return includeBean;
    }


    /**
     * Return an existing <code>ActionComponentBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a IncludeBean
     */
    public ActionComponentBean getActionComponentBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (ActionComponentBean) tagBeans.get(beanKey);
        }
        ActionComponentBean bean = new ActionComponentBean(beanId);
        configureBean(bean);
        tagBeans.put(beanKey, bean);
        return bean;
    }

    /**
     * Return an existing <code>TableBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a TableBean
     */
    public TableBean getTableBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (TableBean) tagBeans.get(beanKey);
        }
        TableBean tb = new TableBean(beanId);
        configureBean(tb);
        tagBeans.put(beanKey, tb);
        return tb;
    }

    /**
     * Return an existing <code>TableRowBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a TableRowBean
     */
    public TableRowBean getTableRowBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (TableRowBean) tagBeans.get(beanKey);
        }
        TableRowBean tr = new TableRowBean(beanId);
        configureBean(tr);
        tagBeans.put(beanKey, tr);
        return tr;
    }

    /**
     * Return an existing <code>TableCellBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a TableCellBean
     */
    public TableCellBean getTableCellBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (TableCellBean) tagBeans.get(beanKey);
        }
        TableCellBean tc = new TableCellBean(beanId);
        configureBean(tc);
        tagBeans.put(beanKey, tc);
        return tc;
    }

    /**
     * Return an existing <code>ListBoxBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a ListBoxBean
     */
    public ListBoxBean getListBoxBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (ListBoxBean) tagBeans.get(beanKey);
        }
        ListBoxBean lb = new ListBoxBean(beanId);
        configureBean(lb);
        tagBeans.put(beanKey, lb);
        return lb;
    }

    /**
     * Return an existing <code>ListBoxItemBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a ListBoxItemBean
     */
    public ListBoxItemBean getListBoxItemBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (ListBoxItemBean) tagBeans.get(beanKey);
        }
        ListBoxItemBean lb = new ListBoxItemBean(beanId);
        configureBean(lb);
        tagBeans.put(beanKey, lb);
        return lb;
    }

    /**
     * Return an existing <code>MessageBoxBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a IncludeBean
     */
    public MessageBoxBean getMessageBoxBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (MessageBoxBean) tagBeans.get(beanKey);
        }
        MessageBoxBean messageBoxBean = new MessageBoxBean(beanId);
        configureBean(messageBoxBean);
        tagBeans.put(beanKey, messageBoxBean);
        return messageBoxBean;
    }

    /**
     * Prints the request parameters to stdout. Generally used for debugging
     */
    public void logRequestParameters() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n\n show request params\n--------------------\n");

        Enumeration e = null;
        if (request != null) {
            e = request.getParameterNames();
        }
        if (portletRequest != null) {
            e = portletRequest.getParameterNames();
        }
        if (e != null) {
            while (e.hasMoreElements()) {
                String name = (String) e.nextElement();
                sb.append("\t\tname :" + name);
                String values[] = null;
                if (request!= null) {
                    values = request.getParameterValues(name);
                }
                if (portletRequest != null) {
                    values = portletRequest.getParameterValues(name);
                }
                if (values != null) {
                    if (values.length == 1) {
                        String pval = values[0];
                        if (!name.startsWith("ui_pb")) sb.append("\t\t value : " + pval);
                    } else {
                        sb.append("\t\t value :");
                        for (int i = 0; i < values.length; i++) {
                            sb.append("\t\t  - " + values[i]);
                        }
                    }
                }
            }
        }
        sb.append("--------------------\n");
        log.debug(sb.toString());
    }

    /**
     * Prints the request attributes to stdout. Generally used for debugging
     */
    public void logRequestAttributes() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n\n show request attributes\n--------------------\n");
        Enumeration e = null;
        if (request != null) {
            e = request.getAttributeNames();
        }
        if (portletRequest != null) {
            e = portletRequest.getAttributeNames();
        }
        if (e != null) {
            while (e.hasMoreElements()) {
                String name = (String) e.nextElement();
                sb.append("name :" + name);
            }
        }
        sb.append("--------------------\n");
        log.debug(sb.toString());
    }

    /**
     * Parses all request parameters for visual beans.
     * A visual bean parameter has the following encoding:
     * ui_<visual bean element>_<bean Id>_name
     * where <visual bean element> is a two letter encoding of the kind of
     * visual bean that it is.
     *
     * @param req the PortletRequest
     */
    protected void createTagBeans(Object req) {
        if (tagBeans == null) tagBeans = new HashMap();
        Map paramsMap;
        // check for file upload
        paramsMap = parseFileUpload(req);
        Enumeration e = null;
        if (request != null) {
            e = request.getParameterNames();
        }
        if (portletRequest != null) {
            e = portletRequest.getParameterNames();
        }
        if (e != null) {
            while (e.hasMoreElements()) {
                String uiname = (String) e.nextElement();
                String[] vals = null;
                if (request != null) {
                    vals = request.getParameterValues(uiname);
                }
                if (portletRequest != null) {
                    vals = portletRequest.getParameterValues(uiname);
                }
                paramsMap.put(uiname, vals);
            }
        }
        //Enumeration enum = request.getParameterNames();

        Iterator it = paramsMap.keySet().iterator();

        while (it.hasNext()) {

            String uiname = (String) it.next();
            String vb = "";
            String name;
            String beanId = "";

            if (!uiname.startsWith("ui")) continue;
            //log.debug("found a tag bean: " + uiname);

            String vbname = uiname.substring(3);


            int idx = vbname.indexOf("_");

            if (idx > 0) {
                vb = vbname.substring(0, idx);
            }

            vbname = vbname.substring(idx + 1);
            idx = vbname.lastIndexOf("_");

            String beanKey;

            if (idx > 0) {
                beanId = vbname.substring(0, idx);
                //log.debug("Parsing beanId...");
                int index = beanId.lastIndexOf("%");
                if (index > -1 && index != beanId.length()) {
                    beanKey = beanId;
                    beanId = beanId.substring(index + 1);
                } else {
                    beanKey = getBeanKey(beanId);
                }
                //log.debug("beanId = " + beanId);
                //log.debug("beankey = " + beanKey);
            } else {
                beanKey = getBeanKey(beanId);

            }

            name = vbname.substring(idx + 1);
            //log.debug("vbname: " + name);

            String[] vals = (String[]) paramsMap.get(uiname);
            

            //log.debug("Adding bean " + beanId + " with bean key " + beanKey);

            if (vb.equals(TextFieldBean.NAME)) {
                //log.debug("Creating a textfieldbean bean with id:" + beanId);
                TextFieldBean bean = new TextFieldBean(beanId);
                bean.setValue(vals[0]);
                //log.debug("setting new value" + vals[0]);
                bean.setName(name);
                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                configureBean(bean);
                tagBeans.put(beanKey, bean);
            } else if (vb.equals(FileInputBean.NAME)) {
                //logRequestAttributes();
                //log.debug("Creating a fileinput bean with id:" + beanId);

                FileInputBean bean;
                FileItem fileItem = null;
                // check whether the fileItems list contains a bean with this name
                if (fileItems != null) {
                    for (int i = 0; i < fileItems.size(); i++) {
                        FileItem item = (FileItem) fileItems.get(i);
                        // if the item is an inputfile item, and the name matches
                        if (!item.isFormField() && item.getFieldName().equals(uiname)) {
                            // then create a FileInputBean with this fileItem
                            fileItem = item;
                            break;
                        }
                    }
                }
                if (fileItem != null) {
                    bean = new FileInputBean(beanId, fileItem);
                } else {
                    bean = new FileInputBean(beanId);
                }
                bean.setName(name);
                configureBean(bean);
                tagBeans.put(beanKey, bean);

                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
            } else if (vb.equals(CalendarBean.NAME)) {
                //log.debug("Creating a calendarbean bean with id:" + beanId);
                CalendarBean bean = new CalendarBean(beanId);
                bean.setValue(vals[0]);
                bean.setName(name);
                configureBean(bean);
                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanKey, bean);
            } else if (vb.equals(CheckBoxBean.NAME)) {
                CheckBoxBean bean = (CheckBoxBean) tagBeans.get(beanKey);
                if (bean == null) {
                    //log.debug("Creating a checkbox bean with id:" + beanId);
                    bean = new CheckBoxBean(beanId);
                    bean.setValue(vals[0]);
                    for (int i = 0; i < vals.length; i++) {
                        String val = vals[i];
                        bean.addSelectedValue(val);
                    }
                    bean.setName(name);
                } else {
                    bean.addSelectedValue(vals[0]);
                }
                bean.setSelected(true);
                configureBean(bean);
                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanKey, bean);
            } else if (vb.equals(ListBoxBean.NAME)) {
                //log.debug("Creating a listbox bean with id:" + beanId);
                ListBoxBean bean = new ListBoxBean(beanId);
                bean.setName(name);
                for (int i = 0; i < vals.length; i++) {
                    ListBoxItemBean item = new ListBoxItemBean();
                    item.setName(vals[i]);
                    item.setValue(vals[i]);
                    item.setSelected(true);
                    //log.debug("adding an item bean: " + vals[i]);
                    bean.addBean(item);
                }
                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                configureBean(bean);
                tagBeans.put(beanKey, bean);
            } else if (vb.equals(RadioButtonBean.NAME)) {
                RadioButtonBean bean = (RadioButtonBean) tagBeans.get(beanKey);
                if (bean == null) {
                    //log.debug("Creating a new radiobutton bean with id:" + beanId);
                    bean = new RadioButtonBean(beanId);
                    bean.setValue(vals[0]);
                    bean.addSelectedValue(vals[0]);
                    bean.setName(name);
                } else {
                    //log.debug("Using existing radiobutton bean with id:" + beanId);
                    bean.addSelectedValue(vals[0]);
                }
                bean.setSelected(true);
                configureBean(bean);
                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanKey, bean);
            } else if (vb.equals(PasswordBean.NAME)) {
                //log.debug("Creating a passwordbean bean with id:" + beanId);
                PasswordBean bean = new PasswordBean(beanId);
                bean.setValue(vals[0]);
                bean.setName(name);
                configureBean(bean);
                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanKey, bean);
            } else if (vb.equals(TextAreaBean.NAME)) {
                //log.debug("Creating a textareabean bean with id:" + beanId);
                TextAreaBean bean = new TextAreaBean(beanId);
                bean.setValue(vals[0]);
                bean.setName(name);
                configureBean(bean);
                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanKey, bean);
            } else if (vb.equals(TextEditorBean.NAME)) {
                //log.debug("Creating a textareabean bean with id:" + beanId);
                TextEditorBean bean = new TextEditorBean(beanId);
                bean.setValue(vals[0]);
                bean.setName(name);
                configureBean(bean);
                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanKey, bean);
            } else if (vb.equals(HiddenFieldBean.NAME)) {
                //log.debug("Creating a hidden bean bean with id:" + beanId);
                HiddenFieldBean bean = new HiddenFieldBean(beanId);
                bean.setValue(vals[0]);
                bean.setName(name);
                configureBean(bean);
                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanKey, bean);
            } else {
                log.error("unable to find suitable bean type for : " + uiname);
            }

            /*
            String values[] = request.getParameterValues(name);
            if (values.length == 1) {
                String pval = values[0];
                if (pval.length() == 0) {
                    pval = "no value";
                }
                System.out.println(" value : " + pval);
            } else {
                System.out.println(" value :");
                for (int i = 0; i < values.length; i++) {
                    System.out.println("            - " + values[i]);
                }
            }
            */

        }
    }

    protected Map parseFileUpload(Object req) {
        //log.debug("parseFileUpload");
        Map parameters = new Hashtable();
        if (req instanceof HttpServletRequest) {
            HttpServletRequest hreq = (HttpServletRequest)req;
            //logRequestParameters();
            //logRequestAttributes();
            ServletRequestContext ctx = new ServletRequestContext(hreq);
            if (FileUpload.isMultipartContent(ctx)) {
                FileItemFactory factory = new DiskFileItemFactory();
                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);
                try {
                    fileItems = upload.parseRequest(hreq);
                } catch (Exception e) {
                    log.error("Error Parsing multi Part form.Error in workaround!!!", e);
                }
                if (fileItems != null) {
                    //log.debug("File items has size " + fileItems.size());
                    for (int i = 0; i < fileItems.size(); i++) {
                        FileItem item = (FileItem) fileItems.get(i);
                        String[] tmpstr = new String[1];
                        if (item.isFormField()) {
                            tmpstr[0] = item.getString();
                        } else {
                            tmpstr[0] = "fileinput";
                        }
                        //log.debug("File item " + item.getFieldName() + "->" + tmpstr[0]);
                        parameters.put(item.getFieldName(), tmpstr);
                    }
                }
            }
        }
        return parameters;
    }

//    protected Map parseFileUpload(Object req) {
//        log.debug("parseFileUpload");
//        Map parameters = new Hashtable();
//        DiskFileItemFactory df = new DiskFileItemFactory();
//        df.setSizeThreshold(FileInputBean.MAX_UPLOAD_SIZE);
//        String tmpDir = System.getProperty("java.io.tmpdir");
//        df.setRepository(new File(tmpDir));
//        if (req instanceof HttpServletRequest) {
//            log.debug("Checking http servlet request for file items");
//            HttpServletRequest hReq = (HttpServletRequest)req;
//            ServletRequestContext ctx = new ServletRequestContext(hReq);
//            if (FileUpload.isMultipartContent(ctx)) {
//                log.debug("File upload event occured");
//                ServletFileUpload sfupload = new ServletFileUpload(df);
//                try {
//                    fileItems = sfupload.parseRequest(hReq);
//                } catch (Exception e) {
//                    log.error("Error Parsing multi Part form.Error in workaround!!!", e);
//                }
//            }
//        }
//        if (req instanceof ActionRequest) {
//            log.debug("Checking action request for file items");
//            ActionRequest areq = (ActionRequest)req;
//            if (PortletFileUpload.isMultipartContent(areq)) {
//                log.debug("File upload event occured");
//                PortletFileUpload pfupload = new PortletFileUpload(df);
//                try {
//                    fileItems = pfupload.parseRequest(areq);
//                } catch (Exception e) {
//                    log.error("Error Parsing multi Part form.Error in workaround!!!", e);
//                }
//            }
//        }
//        if (fileItems != null) {
//            log.debug("Number of file items " + fileItems.size());
//            for (int i = 0; i < fileItems.size(); i++) {
//                FileItem item = (FileItem) fileItems.get(i);
//                String[] tmpstr = new String[1];
//                if (item.isFormField()) {
//                    tmpstr[0] = item.getString();
//                } else {
//                    tmpstr[0] = "fileinput";
//                }
//                log.debug("Name: " + item.getFieldName() + " Value: " + tmpstr[0]);
//                parameters.put(item.getFieldName(), tmpstr);
//            }
//        }
//        return parameters;
//    }

    /**
     * Returns a bean key identifier using the component identifier
     *
     * @param beanId the bean identifier
     * @return the bean key identifier
     */
    protected String getBeanKey(String beanId) {
        String beanKey;
        if (compId == null) {
            beanKey = beanId + '_' + cid;
        } else {
            beanKey = compId + '%' + beanId + '_' + cid;
        }
        //log.debug("BaseFormEventImpl.getBeanKey(" + beanId + ") = " + beanKey);
        return beanKey;
    }

    /**
     * Stores any created beans into the request
     */

    public void store() {
        Iterator it = tagBeans.keySet().iterator();
        TagBean tagBean;
        while (it.hasNext()) {
            String beanKey = (String)it.next();
            tagBean = (TagBean)tagBeans.get(beanKey);
            //log.debug("storing bean in attribute: " + beanKey);
            if (request != null) request.setAttribute(beanKey, tagBean);
            if (portletRequest != null) portletRequest.setAttribute(beanKey, tagBean);
        }
        //logRequestAttributes();
    }

    /**
     * Logs all tag bean identifiers, primarily used for debugging
     */
    public void logTagBeans() {
        //log.debug("in print tag beans:");
        Iterator it = tagBeans.values().iterator();
        while (it.hasNext()) {
            TagBean tagBean = (TagBean) it.next();
            log.debug("tag bean id: " + tagBean.getBeanId());
        }
    }

    public ActionMenuItemBean getActionMenuItemBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (ActionMenuItemBean) tagBeans.get(beanKey);
        }
        ActionMenuItemBean ami = new ActionMenuItemBean(beanId);
        configureBean(ami);
        tagBeans.put(beanKey, ami);
        return ami;
    }

    public DataGridBean getDataGridBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (DataGridBean) tagBeans.get(beanKey);
        }
        DataGridBean dgBean = new DataGridBean(beanId);
        dgBean.setHttpServletRequest(request);
        configureBean(dgBean);
        tagBeans.put(beanKey, dgBean);
        return dgBean;
    }

    public ActionMenuBean getActionMenuBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (ActionMenuBean) tagBeans.get(beanKey);
        }
        ActionMenuBean am = new ActionMenuBean(beanId);
        configureBean(am);
        tagBeans.put(beanKey, am);
        return am;
    }

}