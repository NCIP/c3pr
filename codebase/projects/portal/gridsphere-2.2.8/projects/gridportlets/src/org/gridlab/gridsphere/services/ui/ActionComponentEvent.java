/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionComponentEvent.java,v 1.1.1.1 2007-02-01 20:41:56 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui;

import org.gridlab.gridsphere.portlet.PortletLog;


import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.provider.event.impl.BaseFormEventImpl;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.apache.commons.fileupload.FileItem;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/*
 * The <code>FormEventImpl</code> provides methods for creating/retrieving visual beans
 * from the <code>PortletRequest</code>
 */

public class ActionComponentEvent extends BaseFormEventImpl {

    private transient static PortletLog log = SportletLog.getInstance(ActionComponentEvent.class);

    protected ActionComponent comp = null;

    protected ActionComponentEvent() {

    }

    public ActionComponentEvent(ActionComponent comp) {
        log.debug("ActionComponentEvent(" + compId + ")");
        this.comp = comp;
        if (comp == null) {
            tagBeans = new HashMap();
        } else {
            tagBeans = comp.getTagBeans();
        }
        compId = comp.getComponentId();
    }

    public void process(PortletRequest request, PortletResponse response) {

        portletRequest = request;
        portletResponse = response;

        locale = (Locale) portletRequest.getPortletSession(true).getAttribute(SportletProperties.LOCALE);
        if (locale == null) locale = portletRequest.getLocale();
        if (locale == null) locale = Locale.ENGLISH;
        cid = (String)portletRequest.getAttribute(SportletProperties.COMPONENT_ID);

        log.debug("process(" + compId + ") with cid " + cid);

        createTagBeans(portletRequest);
    }

    public String getComponentId() {
        return compId;
    }

    protected void createTagBeans(Object request) {

        PortletRequest req = (PortletRequest)request;

        Map paramsMap = new HashMap();

        // check for file upload
        paramsMap = parseFileUpload(req);

        Enumeration e = req.getParameterNames();

        log.debug("createTagBeans() request params " + req.getParameterMap().size());

        while (e.hasMoreElements()) {
            String uiname = (String) e.nextElement();

            log.debug("createTagBeans() request contains parameter " + uiname);

            String[] vals = req.getParameterValues(uiname);
            paramsMap.put(uiname, vals);
        }

        // Need to keep track of what check box beans we found
        // For all check beans that exist in tag beans but
        // were not found in the request, need to set them to false.
        // See below...
        Map checkBoxBeans = new HashMap();

        Iterator it = paramsMap.keySet().iterator();

        while (it.hasNext()) {

            String uiname = (String) it.next();
            String vb = "";
            String name = "";
            String beanId = "";
            String beanKey = "";

            log.debug("createTagBeans() Checking parameter " + uiname);

            if (!uiname.startsWith("ui")) continue;

            log.debug("createTagBeans() Found a tag bean: " + uiname);

            String vbname = uiname.substring(3);


            int idx = vbname.indexOf("_");

            if (idx > 0) {
                vb = vbname.substring(0, idx);
            }

            vbname = vbname.substring(idx + 1);
            idx = vbname.indexOf("_");

            if (idx > 0) {
                beanId = vbname.substring(0, idx);
                log.debug("createTagBeans() Parsing beanId...");
                int index = beanId.lastIndexOf("%");
                if (index > -1 && index != beanId.length()) {
                    String beanCompId = beanId.substring(0,index);
                    if (beanCompId.equals(compId)) {
                        log.debug("createTagBeans() This bean belongs to component " + compId);
                        beanId = beanId.substring(index+1);
                        beanKey = getBeanKey(beanId);
                    } else {
                        log.debug("createTagBeans() Skipping tag bean..." + uiname);
                        continue;
                    }
                } else {
                    log.debug("createTagBeans() Skipping tag bean..." + uiname);
                    continue;
                }
                log.debug("createTagBeans() beanId = " + beanId);
                log.debug("createTagBeans() beankey = " + beanKey);
            } else {
                log.debug("createTagBeans() Skipping tag bean..." + uiname);
                continue;
            }

            name = vbname.substring(idx + 1);
            log.debug("createTagBeans() vbname: " + name);

            String[] vals = (String[]) paramsMap.get(uiname);

            log.debug("createTagBeans() Adding bean " + beanId + " with bean key " + beanKey);

            if (vb.equals(TextFieldBean.NAME)) {
                TextFieldBean bean = (TextFieldBean)tagBeans.get(beanId);
                if (bean == null) {
                    bean = new TextFieldBean(beanId);
                    bean.setName(name);
                    log.debug("createTagBeans() Created TextFieldBean " + beanId);
                    tagBeans.put(beanId, bean);
                } else {
                    log.debug("createTagBeans() Found TextFieldBean " + beanId);
                }
                bean.setValue(vals[0]);
                log.debug("createTagBeans() Set TextFieldBean bean value " + vals[0]);
            } else if (vb.equals(FileInputBean.NAME)) {

                log.debug("createTagBeans() Found a FileInputBean with id:" + beanId);
                try {
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
                    FileInputBean bean = (FileInputBean)tagBeans.get(beanId);
                    if (bean == null) {
                        bean = new FileInputBean(beanId, fileItem);
                        log.debug("createTagBeans() Created FileInputBean " + beanId);
                        tagBeans.put(beanKey, bean);
                    } else {
                         log.debug("createTagBeans() Updated FileInputBean " + beanId);
                         bean.setName(name);
                         bean.setFileItem(fileItem);
                    }
                } catch (Exception ex) {
                    log.error("Unable to create file input bean", ex);
                }

            } else if (vb.equals(CheckBoxBean.NAME)) {
                CheckBoxBean bean = (CheckBoxBean) tagBeans.get(beanId);
                if (bean == null) {
                    log.debug("createTagBeans() Creating a checkbox bean with id:" + beanId);
                    bean = new CheckBoxBean(beanId);
                    bean.setName(name);
                    tagBeans.put(beanId, bean);
                } else {
                    log.debug("createTagBeans() Found a checkbox bean with id:" + beanId);
                    bean.clearSelectedValues();
                }
                // Set the values...
                bean.setValue(vals[0]);
                bean.setSelected(true);
                for (int i = 0; i < vals.length; i++) {
                    String val = vals[i];
                    bean.addSelectedValue(val);
                }
                // Store bean in check box bean map (see below)
                checkBoxBeans.put(beanId, bean);
            } else if (vb.equals(ListBoxBean.NAME)) {
                log.debug("createTagBeans() Creating a ListBoxBean " + beanId);
                ListBoxBean bean = (ListBoxBean)tagBeans.get(beanId);
                if (bean == null) {
                    bean = new ListBoxBean(beanId);
                    bean.setName(name);
                    for (int i = 0; i < vals.length; i++) {
                        ListBoxItemBean item = new ListBoxItemBean();
                        item.setName(vals[i]);
                        item.setValue(vals[i]);
                        item.setSelected(true);
                        log.debug("createTagBeans() adding an item bean: " + vals[i]);
                        bean.addBean(item);
                    }
                    tagBeans.put(beanId, bean);
                } else {
                    log.debug("createTagBeans() Updating ListBoxBean " + beanId);
                    List items = bean.getBeans();
                    for (int j = 0; j < items.size(); ++j)  {
                        ListBoxItemBean item = (ListBoxItemBean)items.get(j);
                        item.setSelected(false);
                    }
                    for (int i = 0; i < vals.length; i++) {
                        log.debug("createTagBeans() Request contains selected value... " + vals[i]);
                        for (int j = 0; j < items.size(); ++j)  {
                            ListBoxItemBean item = (ListBoxItemBean)items.get(j);
                            if (item.getName() != null && item.getName().equals(vals[i])) {
                                log.debug("createTagBeans() Found value in list " + item.getName());
                                item.setSelected(true);
                            }
                        }
                    }
                }
            } else if (vb.equals(RadioButtonBean.NAME)) {
                RadioButtonBean bean = (RadioButtonBean) tagBeans.get(beanId);
                if (bean == null) {
                    log.error("Creating a radio button bean with id:" + beanId);
                    bean = new MyRadioButtonBean(beanId);
                    bean.setName(name);
                    tagBeans.put(beanId, bean);
                } else {
                    log.error("Updating radio button bean with id:" + beanId);
                    bean.clearSelectedValues();
                }
                bean.addSelectedValue(vals[0]);
                log.error("Setting selected value to " + vals[0]);
                log.error("Testing selected value " + bean.getSelectedValue());
            } else if (vb.equals(PasswordBean.NAME)) {
                log.debug("createTagBeans() Creating a passwordbean bean with id:" + beanId);
                PasswordBean bean = (PasswordBean)tagBeans.get(beanId);
                if (bean == null) {
                    bean = new PasswordBean(beanId);
                    bean.setName(name);
                    tagBeans.put(beanId, bean);
                }
                bean.setValue(vals[0]);
            } else if (vb.equals(TextAreaBean.NAME)) {
                log.debug("createTagBeans() Creating a textareabean bean with id:" + beanId);
                TextAreaBean bean = (TextAreaBean)tagBeans.get(beanId);
                if (bean == null) {
                    bean = new TextAreaBean(beanId);
                    bean.setName(name);
                    tagBeans.put(beanId, bean);
                }
                bean.setValue(vals[0]);
            } else if (vb.equals(HiddenFieldBean.NAME)) {
                log.debug("createTagBeans() Creating HiddenFieldBean " + beanId);
                HiddenFieldBean bean = (HiddenFieldBean)tagBeans.get(beanId);
                if (bean == null) {
                    bean = new HiddenFieldBean(beanId);
                    bean.setName(name);
                    tagBeans.put(beanId, bean);
                } else {
                    log.debug("createTagBeans() Updating HiddenFieldBean " + beanId);
                    log.debug("createTagBeans() Old value = " + bean.getValue());
                }
                log.debug("createTagBeans() Setting value to " + vals[0]);
                bean.setValue(vals[0]);
            } else {
                log.error("Unable to find suitable bean type for : " + uiname);
            }
        }

        // Now check to make sure we have our check box values right
        Iterator tagBeanValues = tagBeans.values().iterator();
        while (tagBeanValues.hasNext()) {
            TagBean tagBean = (TagBean)tagBeanValues.next();
            if (tagBean instanceof CheckBoxBean) {
                CheckBoxBean checkBoxBean = (CheckBoxBean)tagBean;
                String beanId = checkBoxBean.getBeanId();
                if (checkBoxBeans.containsKey(beanId)) {
                    log.debug("createTagBeans() Check box bean " + beanId + " has been set in request");
                } else {
                    log.debug("createTagBeans() Check box bean " + beanId + " was not set in request");
                    checkBoxBean.clearSelectedValues();
                }
            }
        }
    }

    public void store() {
        Iterator it = tagBeans.keySet().iterator();
        BaseBean tagBean = null;
        while (it.hasNext()) {
            String beanId = (String)it.next();
            tagBean = (BaseBean)tagBeans.get(beanId);
            configureBean(tagBean);
            String beanKey = tagBean.getBeanKey();
            log.debug("Storing bean with bean id " + beanId + " and bean key " + beanKey);
            if (request != null) request.setAttribute(beanKey, tagBean);
            if (portletRequest != null) portletRequest.setAttribute(beanKey, tagBean);
        }
        logRequestAttributes();
    }
}
