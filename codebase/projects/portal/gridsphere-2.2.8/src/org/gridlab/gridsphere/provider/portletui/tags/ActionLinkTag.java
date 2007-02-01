/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ActionLinkTag.java 4915 2006-07-10 20:51:49Z novotny $
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.provider.portletui.beans.ActionLinkBean;
import org.gridlab.gridsphere.provider.portletui.beans.ImageBean;
import org.gridlab.gridsphere.provider.portletui.beans.MessageStyle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import java.util.ArrayList;

/**
 * The <code>ActionLinkTag</code> provides a hyperlink element that includes a <code>DefaultPortletAction</code>
 * and can contain nested <code>ActionParamTag</code>s
 */
public class ActionLinkTag extends ActionTag {

    protected ActionLinkBean actionlink = null;

    protected String style = MessageStyle.MSG_INFO;
    protected ImageBean imageBean = null;

    /**
     * Sets the style of the text: Available styles are
     * <ul>
     * <li>nostyle</li>
     * <li>error</li>
     * <li>info</li>
     * <li>status</li>
     * <li>alert</li>
     * <li>success</li>
     *
     * @param style the text style
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Returns the style of the text: Available styles are
     * <ul>
     * <li>nostyle</li>
     * <li>error</li>
     * <li>info</li>
     * <li>status</li>
     * <li>alert</li>
     * <li>success</li>
     *
     * @return the text style
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the action link key used to locate localized text
     *
     * @param key the action link key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Returns the action link key used to locate localized text
     *
     * @return the action link key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the image bean
     *
     * @param imageBean the image bean
     */
    public void setImageBean(ImageBean imageBean) {
        this.imageBean = imageBean;
    }

    /**
     * Returns the image bean
     *
     * @return the image bean
     */
    public ImageBean getImageBean() {
        return imageBean;
    }

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            actionlink = (ActionLinkBean) getTagBean();
            if (actionlink == null) {
                actionlink = new ActionLinkBean(beanId);
                actionlink.setStyle(style);
                this.setBaseComponentBean(actionlink);
            } else {
                if (actionlink.getParamBeanList() != null) {
                    paramBeans = actionlink.getParamBeanList();
                }
                if (actionlink.getAction() != null) {
                    action = actionlink.getAction();
                }
                if (actionlink.getValue() != null) {
                    value = actionlink.getValue();
                }
                if (actionlink.getKey() != null) {
                    key = actionlink.getKey();
                }
                if (actionlink.getOnClick() != null) {
                    onClick = actionlink.getOnClick();
                }
            }
        } else {
            actionlink = new ActionLinkBean();
            this.setBaseComponentBean(actionlink);
            actionlink.setStyle(style);
        }

        actionlink.setUseAjax(useAjax);
        if (name != null) actionlink.setName(name);
        if (anchor != null) actionlink.setAnchor(anchor);
        if (action != null) actionlink.setAction(action);
        if (value != null) actionlink.setValue(value);
        if (trackMe != null) actionlink.setTrackme(trackMe);
        if (extUrl != null) actionlink.setExturl(extUrl);
        if (onClick != null) actionlink.setOnClick(onClick);

        Tag parent = getParent();
        if (parent instanceof ActionMenuTag) {
            ActionMenuTag actionMenuTag = (ActionMenuTag) parent;
            if (!actionMenuTag.getLayout().equals("horizontal")) {
                actionlink.setCssStyle("display: block");
            }
        }

        if (key != null) {
            actionlink.setKey(key);
            actionlink.setValue(getLocalizedText(key));
            value = actionlink.getValue();
        }

        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {

        if (!beanId.equals("")) {
            paramBeans = actionlink.getParamBeanList();
            label = actionlink.getLabel();
            trackMe = actionlink.getTrackme();
            extUrl = actionlink.getExturl();
            action = actionlink.getAction();
        }

        actionlink.setPortletURI(createActionURI());
        if ((bodyContent != null) && (value == null)) {
            actionlink.setValue(bodyContent.getString());
        }

        if (useAjax) {
            String cid = (String)pageContext.getRequest().getAttribute(SportletProperties.COMPONENT_ID);
            String paction = ((!action.equals("")) ? "&" + portletAction.toString() : "");
            actionlink.setOnClick("GridSphereAjaxHandler.startRequest(" + cid + ", '" + paction + "');");
        }

        if (imageBean != null) {
            String val = actionlink.getValue();
            if (val == null) val = "";
            actionlink.setValue(imageBean.toStartString() + val);
        }

        Tag parent = getParent();
        if (parent instanceof DataGridColumnTag) {
            DataGridColumnTag dataGridColumnTag = (DataGridColumnTag) parent;
            dataGridColumnTag.addTagBean(this.actionlink);
        } else {

            try {
                JspWriter out = pageContext.getOut();
                out.print(actionlink.toEndString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return EVAL_PAGE;
    }
}
