/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: FormEvent.java,v 1.1.1.1 2007-02-01 20:50:58 kherm Exp $
 */
package org.gridlab.gridsphere.provider.event;


import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.provider.portletui.beans.*;

import java.io.IOException;
import java.util.Map;

/**
 * The <code>FormEvent</code> provides a decorator around a <code>ActionEvent</code> and is used in the GridSphere
 * <code>ActionPortlet</code> model to create and store visual beans representing user interfaces.
 */
public interface FormEvent extends ActionEvent {

    /**
     * Return an existing <code>ActionLinkBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a ActionLinkBean
     */
    public ActionLinkBean getActionLinkBean(String beanId);

    /**
     * Return an existing <code>ActionParamBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a ActionParamBean
     */
    public ActionParamBean getActionParamBean(String beanId);

    /**
     * Return an existing <code>ActionSubmitBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a ActionSubmitBean
     */
    public ActionSubmitBean getActionSubmitBean(String beanId);

    /**
     * Return an existing <code>FrameBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a FrameBean
     */
    public FrameBean getFrameBean(String beanId);

    /**
     * Return an existing <code>FileInputBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a FileInputBean
     */
    public FileInputBean getFileInputBean(String beanId) throws IOException;

    /**
     * Return an existing <code>CheckBoxBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a CheckBoxBean
     */
    public CheckBoxBean getCheckBoxBean(String beanId);

    /**
     * Return an existing <code>RadioButtonBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a RadioButtonBean
     */
    public RadioButtonBean getRadioButtonBean(String beanId);

    /**
     * Return an existing <code>TextFieldBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a TextFieldBean
     */
    public TextFieldBean getTextFieldBean(String beanId);

    /**
     * Return an existing <code>TextEditorBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a TextEditorBean
     */
    public TextEditorBean getTextEditorBean(String beanId);

    /**
     * Return an existing <code>HiddenFieldBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a HiddenFieldBean
     */
    public HiddenFieldBean getHiddenFieldBean(String beanId);

    /**
     * Return an existing <code>PasswordBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a PasswordBean
     */
    public PasswordBean getPasswordBean(String beanId);

    /**
     * Return an existing <code>TextAreaBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a TextAreaBean
     */
    public TextAreaBean getTextAreaBean(String beanId);

    /**
     * Return an existing <code>TextBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a TextBean
     */
    public TextBean getTextBean(String beanId);

    /**
     * Return an existing <code>ImageBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a ImageBean
     */
    public ImageBean getImageBean(String beanId);

    /**
     * Return an existing <code>ListBoxBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a ListBoxBean
     */
    public ListBoxBean getListBoxBean(String beanId);

    /**
     * Return an existing <code>PanelBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a PanelBean
     */
    public PanelBean getPanelBean(String beanId);

    /**
     * Return an existing <code>TableCellBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a TableCellBean
     */
    public TableCellBean getTableCellBean(String beanId);

    /**
     * Return an existing <code>TableRowBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a TableRowBean
     */
    public TableRowBean getTableRowBean(String beanId);

    /**
     * Return an existing <code>TableBean</code> or create a new one
     *
     * @param beanId the bean identifier
     * @return a TableBean
     */
    public TableBean getTableBean(String beanId);

    /**
     * Returns the collection of visual tag beans contained by this form event
     *
     * @return the collection of visual tag beans
     */
    public Map getTagBeans();

    public ActionMenuBean getActionMenuBean(String beanId);

    public ActionMenuItemBean getActionMenuItemBean(String beanId);

    public DataGridBean getDataGridBean(String beanId);

    /**
     * Return an existing <code>IncludeBean</code> or create a new one
     *
     * @param beanId
     * @return an include bean
     */
    public IncludeBean getIncludeBean(String beanId);

    /**
     * Return an existing <code>ActionComponentBean</code> or create a new one
     *
     * @param beanId
     * @return an action component bean
     */
    public ActionComponentBean getActionComponentBean(String beanId);

    /**
     * Return an existing <code>MessageBoxBean</code> or create a new one
     *
     * @param beanId
     * @return a message box bean
     */
    public MessageBoxBean getMessageBoxBean(String beanId);

    /**
     * Stores any created beans into the request
     */
    public void store();

}
