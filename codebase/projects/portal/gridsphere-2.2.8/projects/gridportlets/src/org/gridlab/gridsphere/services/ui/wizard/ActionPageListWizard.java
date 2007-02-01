/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionPageListWizard.java,v 1.1.1.1 2007-02-01 20:42:23 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.wizard;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponent;
import org.gridlab.gridsphere.services.ui.ActionComponentState;
import org.gridlab.gridsphere.services.ui.*;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.ActionMenuBean;
import org.gridlab.gridsphere.provider.portletui.beans.ActionMenuItemBean;
import org.gridlab.gridsphere.provider.portletui.beans.ActionSubmitBean;

import javax.portlet.PortletException;
import java.util.List;
import java.util.Vector;
import java.util.Map;

public abstract class ActionPageListWizard
        extends ActionComponent
        implements ActionPageWizard {

    private transient static PortletLog log = SportletLog.getInstance(ActionPageListWizard.class);

    protected List pageList = new Vector();
    protected int pageNumber = 1;
    protected ActionPage currentPage = null;
    protected ActionComponentFrame pageBean = null;
    protected ActionMenuBean pageMenu = null;
    protected ActionComponentState submitState = null;
    protected ActionComponentState cancelState = null;
    Boolean prevPageFlag = Boolean.TRUE;
    Boolean nextPageFlag = Boolean.TRUE;

    public ActionPageListWizard(ActionComponentFrame container, String compName) {
        super(container, compName);
        pageBean = createPageBean();
        pageMenu = createPageMenu();
        setDefaultAction("doWizard");
    }

    protected ActionComponentFrame createPageBean() {
        return createActionComponentFrame(PAGE_BEAN_ID);
    }

    protected ActionMenuBean createPageMenu() {
        ActionMenuBean menuBean = createActionMenuBean(PAGE_MENU_ID);
        menuBean.setAlign(ActionMenuBean.MENU_HORIZONTAL);
        return menuBean;
    }

    public ActionComponentState getSubmitState() {
        return submitState;
    }

    public void setSubmitState(Class actionClass, String method, Map parameters) {
        submitState = new ActionComponentState(actionClass, method, parameters);
    }

    public ActionComponentState getCancelState() {
        return cancelState;
    }

    public void setCancelState(Class actionClass, String method, Map parameters) {
        cancelState = new ActionComponentState(actionClass, method, parameters);
    }

    public List getPageList() {
        return pageList;
    }

    public void addPage(Class pageClass) {
        pageList.add(pageClass);
    }

    public void removePage(int pageNumber) {
        pageList.remove(pageNumber-1);
    }

    public void clearPages() {
        pageList.clear();
    }

    public ActionPage getCurrentPage() {
        return currentPage;
    }

    public int getNumberOfPages() {
        return pageList.size();
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public boolean hasPreviousPage() {
        return (pageNumber > 1);
    }

    public boolean hasNextPage() {
        return (pageNumber < pageList.size());
    }

    public void loadActionMenu() {
        pageMenu.clear();
        ActionMenuItemBean prevPageItem = createPreviousPageItem();
        pageMenu.addMenuEntry(prevPageItem);
        ActionMenuItemBean currPageItem = createRefreshPageItem();
        pageMenu.addMenuEntry(currPageItem);
        ActionMenuItemBean nextPageItem = createNextPageItem();
        pageMenu.addMenuEntry(nextPageItem);
        ActionMenuItemBean cnclPageItem = createCancelItem();
        pageMenu.addMenuEntry(cnclPageItem);
    }

    protected void onStore() throws PortletException {
        super.onInit();
        log.debug("Setting prevPageFlag to " + prevPageFlag);
        log.debug("Setting nextPageFlag to " + nextPageFlag);
        setPageAttribute("prevPageFlag", prevPageFlag);
        setPageAttribute("nextPageFlag", nextPageFlag);
    }

    public ActionMenuItemBean createRefreshPageItem() {
        ActionMenuItemBean pageItem = new ActionMenuItemBean();
        pageItem.setMenutype(ActionMenuBean.TYPE_ACTIONBAR);
        ActionSubmitBean pageSubmit = createActionSubmitBean("refreshPageAction");
        pageSubmit.setName("doRefreshPage");
        pageSubmit.setValue("&lt;Refresh&gt;");
        pageItem.addBean(pageSubmit);
        return pageItem;
    }

    public ActionMenuItemBean createPreviousPageItem() {
        ActionMenuItemBean pageItem = new ActionMenuItemBean();
        pageItem.setMenutype(ActionMenuBean.TYPE_ACTIONBAR);
        ActionSubmitBean pageSubmit = createActionSubmitBean("previousPageAction");
        pageSubmit.setName("doPreviousPage");
        pageSubmit.setValue("&lt;&lt;Previous");
        if (hasPreviousPage()) {
            prevPageFlag = Boolean.TRUE;
        } else {
            prevPageFlag = Boolean.FALSE;
            pageSubmit.setReadOnly(true);
            pageSubmit.setDisabled(true);
        }
        pageItem.addBean(pageSubmit);
        return pageItem;
    }

    public ActionMenuItemBean createNextPageItem() {
        ActionMenuItemBean pageItem = new ActionMenuItemBean();
        pageItem.setMenutype(ActionMenuBean.TYPE_ACTIONBAR);
        ActionSubmitBean pageSubmit = createActionSubmitBean("nextPageAction");
        if (hasNextPage()) {
            pageSubmit.setName("doNextPage");
            pageSubmit.setValue("Next&gt;&gt;");
            nextPageFlag = Boolean.TRUE;
        } else {
            nextPageFlag = Boolean.FALSE;
            pageSubmit.setName("doSubmitWizard");
            pageSubmit.setValue("Submit");
        }
        pageItem.addBean(pageSubmit);
        return pageItem;
    }

    public ActionMenuItemBean createCancelItem() {
        ActionMenuItemBean pageItem = new ActionMenuItemBean();
        pageItem.setMenutype(ActionMenuBean.TYPE_ACTIONBAR);
        ActionSubmitBean pageSubmit = createActionSubmitBean("cancelWizardAction");
        pageSubmit.setName("doCancelWizard");
        pageSubmit.setValue("Cancel");
        pageItem.addBean(pageSubmit);
        return pageItem;
    }

    public void doWizard(Map parameters) throws PortletException {
        log.error("doWizard()");
        if (pageList.size() == 0) {
            throw new PortletException("There are no pages in the wizard!");
        }
        pageNumber = 1;
        Class pageClass = (Class)pageList.get(0);
        pageBean.doAction(pageClass, DEFAULT_ACTION, parameters);
        currentPage = (ActionPage)pageBean.getActiveComponent();
        loadActionMenu();
        setNextState(defaultJspPage);
    }

    public void doPreviousPage(Map parameters) throws PortletException {
        log.error("doPreviousPage()");
        if (currentPage == null) {
            throw new PortletException("There is no active page component!");
        }
        if (hasPreviousPage()) {
            int prevPageNumber = pageNumber-1;
            setPreviousPageParameters(prevPageNumber, parameters);
            Class prevPageClass = (Class)pageList.get(prevPageNumber-1);
            log.debug("Previous page class is " + prevPageClass.getName());
            pageBean.doAction(prevPageClass, DEFAULT_ACTION, parameters);
            pageNumber = prevPageNumber;
            currentPage = (ActionPage)pageBean.getActiveComponent();
        } else {
            messageBox.appendText("No page exists before page number " + pageNumber);
        }
        loadActionMenu();
        setNextState(defaultJspPage);
    }

    public void doRefreshPage(Map parameters) throws PortletException {
        log.error("doRefreshPage()");
        if (currentPage == null) {
            throw new PortletException("There is no active page component!");
        }
        loadActionMenu();
        setNextState(defaultJspPage);
    }

    public void doNextPage(Map parameters) throws PortletException {
        log.error("doNextPage()");
        if (currentPage == null) {
            throw new PortletException("There is no active page component!");
        }
        if (hasNextPage()) {
            setCurrentPageParameters(pageNumber, parameters);
            if (currentPage.validatePage(parameters)) {
                int nextPageNumber = pageNumber+1;
                setNextPageParameters(nextPageNumber, parameters);
                Class nextPageClass = (Class)pageList.get(nextPageNumber-1);
                log.debug("Next page class is " + nextPageClass.getName());
                pageBean.doAction(nextPageClass, DEFAULT_ACTION, parameters);
                pageNumber = nextPageNumber;
                currentPage = (ActionPage)pageBean.getActiveComponent();
            } else {
                pageBean.doRender(parameters);
            }
        } else {
            messageBox.appendText("No page exists after page number " + pageNumber);
        }
        loadActionMenu();
        setNextState(defaultJspPage);
    }

    public void doSubmitWizard(Map parameters) throws PortletException {
        log.error("doSubmitWizard()");
        if (currentPage == null) {
            throw new PortletException("There is no active page component!");
        }
        if (submitState == null) {
            throw new PortletException("Submit state was not set for this wizard");
        }
        setCurrentPageParameters(pageNumber, parameters);
        if (currentPage.validatePage(parameters)) {
            try {
                if (doSubmitPages(parameters)) {
                    submitState.getParameters().putAll(parameters);
                    setNextState(submitState);
                }
            } catch (Exception e) {
                log.error("doSubmitPages failed", e);
                messageBox.appendText(e.getMessage());
            }
        }
    }

//    protected boolean validatePate(ActionPage page, Map parameters) throws PortletException {
//        return page.validatePage(parameters);
//    }

    public abstract boolean doSubmitPages(Map parameters) throws PortletException;

    public void doCancelWizard(Map parameters) throws PortletException {
        log.error("doCancelWizard()");
        if (currentPage == null) {
            throw new PortletException("There is no active page component!");
        }
        if (cancelState == null) {
            throw new PortletException("Cancel state was not set for this wizard");
        }
        try {
            doCancelPages(parameters);
            setNextState(cancelState);
        } catch (Exception e) {
            log.error("doCancelPages failed", e);
            messageBox.appendText(e.getMessage());
        }
    }

    public void doCancelPages(Map parameters) throws PortletException {
        log.debug("doCancelPages() not defined for this wizard");
    }

    public void setPreviousPageParameters(int pageNumber, Map parameters)
            throws PortletException {
        log.debug("setPreviousPageParameters() not defined for this wizard");
    }

    public void setCurrentPageParameters(int pageNumber, Map parameters)
            throws PortletException {
        log.debug("setCurrentPageParameters() not defined for this wizard");
    }

    public void setNextPageParameters(int pageNumber, Map parameters)
            throws PortletException {
        log.debug("setNextPageParameters() not defined for this wizard");
    }
}
