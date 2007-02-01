<%@ page import="org.gridlab.gridsphere.services.file.FileTask,
                 org.gridlab.gridsphere.services.ui.ActionComponent,
                 org.gridlab.gridsphere.services.ui.file.FileActivityComp,
                 org.gridlab.gridsphere.portlet.impl.SportletProperties,
                 org.gridlab.gridsphere.services.core.utils.DateUtil,
                 java.util.*,
                 java.text.DateFormat,
                 org.gridlab.gridsphere.services.util.GridPortletsResourceBundle"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<%
    List fileTaskList = (List)ActionComponent.getPageAttribute(request, "fileTaskList", new Vector());
    Boolean deletedFlag = (Boolean)ActionComponent.getPageAttribute(request, "deletedFlag", Boolean.FALSE);
%>
<ui:table width="100%">
    <ui:tablerow>
        <ui:tablecell>
<% if (deletedFlag.booleanValue()) { %>
            <ui:actionsubmit action="doShowActivity" value="<< List Activity"/>
<% } else { %>
            <ui:actionsubmit action="doShowActivity" value="Refresh List"/>
<% if (fileTaskList.size() > 0) { %>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doDeleteActivity" value="Delete Tasks"/>
<% }
} %>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
<ui:table sortable="true" width="100%">
        <ui:tablerow header="true">
<% if (!deletedFlag.booleanValue()) { %>
            <ui:tablecell width="5">
                <ui:text value="&nbsp;"/>
            </ui:tablecell>
<% } %>
            <ui:tablecell>
                <ui:text value="Items"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Task Type"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Task Status"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Date Submitted"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Date Ended"/>
            </ui:tablecell>
        </ui:tablerow>
<%  Iterator fileTaskIterator = fileTaskList.iterator();
while (fileTaskIterator.hasNext()) {
    FileTask task = (FileTask)fileTaskIterator.next();
    String taskOid = task.getOid();
    Locale locale = request.getLocale();
    String taskSummary = GridPortletsResourceBundle.getResourceString(locale, "NONE", "unknown");
    taskSummary = FileActivityComp.getFileTaskSummary(task, taskSummary);
    String taskType = task.getTaskType().getName(locale);
    String taskStatus = task.getTaskStatus().getName(locale);
    String statusMessage = task.getTaskStatusMessage();
    if (statusMessage != null) {
        taskStatus += " : " + statusMessage;
    }
    String timeSubmitted = "&nbsp";
    Date dateSubmitted = task.getDateTaskSubmitted();
    if (dateSubmitted != null) {
        timeSubmitted = DateUtil.getLocalizedDate(task.getUser(),
                                                  locale, dateSubmitted.getTime(),
                                                  DateFormat.MEDIUM,
                                                  DateFormat.MEDIUM);
    }
    String timeEnded = "&nbsp";
    Date dateEnded = task.getDateTaskEnded();
    if (dateEnded != null) {
        timeEnded = DateUtil.getLocalizedDate(task.getUser(),
                                              locale, dateEnded.getTime(),
                                              DateFormat.MEDIUM,
                                              DateFormat.MEDIUM);
    }
%>
        <ui:tablerow>
<% if (!deletedFlag.booleanValue()) { %>
            <ui:tablecell width="5">
                <ui:checkbox beanId="fileTaskOidCheckBox" value="<%=taskOid%>"/>
            </ui:tablecell>
<% } %>
            <ui:tablecell>
                <ui:text value="<%=taskSummary%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=taskType%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=taskStatus%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=timeSubmitted%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=timeEnded%>"/>
            </ui:tablecell>
        </ui:tablerow>
<%
}
%>
</ui:table>
