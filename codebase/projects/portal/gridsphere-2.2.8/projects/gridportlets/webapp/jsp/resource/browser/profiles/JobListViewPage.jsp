<%@ page import="org.gridlab.gridsphere.services.job.Job,
                 org.gridlab.gridsphere.services.job.JobSpec,
                 org.gridlab.gridsphere.services.task.TaskStatus,
                 java.util.Iterator,
                 java.util.List,
                 java.util.ArrayList,
                 org.gridlab.gridsphere.services.ui.ActionComponent,
                 org.gridlab.gridsphere.portlet.impl.SportletProperties,
                 org.gridlab.gridsphere.services.ui.resource.browser.ResourceComponent,
                 org.gridlab.gridsphere.services.file.FileLocation,
                 org.gridlab.gridsphere.services.ui.resource.browser.BaseResourcePage,
                 org.gridlab.gridsphere.services.resource.Resource,
                 org.gridlab.gridsphere.services.job.JobQueueType"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="/oscache" prefix="oscache" %>
<portlet:defineObjects/>
<% String compId = (String) request.getAttribute(SportletProperties.GP_COMPONENT_ID);
   String key = compId + "generalJobListView";
   Boolean refreshContent = (Boolean)ResourceComponent.getPageAttribute(request, "refreshContent", Boolean.FALSE); %>
<oscache:cache key="<%=key%>" refresh="<%=refreshContent.booleanValue()%>">
<%
    List jobList = (List)ActionComponent.getPageAttribute(request, "resourceList", new ArrayList());
    String lastUpdated = (String)ResourceComponent.getPageAttribute(request, BaseResourcePage.LAST_UPDATED, "");
%>
    <h3>Job List <%=lastUpdated%></h3>
    <ui:table zebra="true" sortable="true" width="100%">
        <ui:tablerow header="true">
<%--            <ui:tablecell>--%>
<%--                <ui:text value="&nbsp;"/>--%>
<%--            </ui:tablecell>--%>
            <ui:tablecell>
                <ui:text value="Job Id"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Name"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Resource"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Scheduler"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Queue"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Stdout"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Stderr"/>
            </ui:tablecell>
        </ui:tablerow>
<%  Iterator jobIterator = jobList.iterator();
    while (jobIterator.hasNext()) {
        Job job = (Job)jobIterator.next();
        String jobDn = job.getDn();
        String jobId = job.getJobId();
        String jobDescription = job.getDescription();
        if (jobDescription == null) jobDescription = "";
        String hostName = job.getHostName();
        if (hostName == null) hostName = "";
        String schedulerName = job.getSchedulerName();
        if (schedulerName == null) schedulerName = "";
        String queueName = job.getQueueName();
        String queueDn = null;
        if (queueName == null) {
            queueName = "";
        } else {
            Resource resource = job.getParentResource();
            if (resource != null && resource.isResourceType(JobQueueType.INSTANCE)) {
                queueDn = resource.getDn();
            }
        }
    %>
           <ui:tablerow>
<%--                <ui:tablecell>--%>
<%--                    <ui:checkbox beanId="jobOidCheckBox" value="<%=jobDn%>"/>--%>
<%--                </ui:tablecell>--%>
                <ui:tablecell>
<%--                    <ui:actionlink action="doResourceView" value="<%=jobId%>">--%>
<%--                        <ui:actionparam name="<%=ResourceComponent.RESOURCE_DN_PARAM%>" value="<%=jobDn%>"/>--%>
<%--                    </ui:actionlink>--%>
                    <ui:text value="<%=jobId%>"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text value="<%=jobDescription%>"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text value="<%=hostName%>"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text value="<%=schedulerName%>"/>
                </ui:tablecell>
                <ui:tablecell>
                <% if (queueDn == null) { %>
                    <ui:text value="<%=queueName%>"/>
                <% } else { %>
                    <ui:actionlink action="+root+doSelectTab" value="<%=queueName%>">
                        <ui:actionparam name="resourceTypeID" value="JobQueue"/>
                        <ui:actionparam name="resourceDn" value="<%=queueDn%>"/>
                    </ui:actionlink>
                <% } %>
                </ui:tablecell>
                <ui:tablecell>
<%
    FileLocation stdout = job.getStdoutLocation();
    if (stdout != null) {
        String stdoutPath = stdout.getFilePath();
        if (!stdoutPath.startsWith("/dev")) {
        String stdoutHost = stdout.getHost(); %>
                    <ui:actionlink action="doDownloadFile" value="stdout">
                        <ui:actionparam name="fileHost" value="<%=stdoutHost%>"/>
                        <ui:actionparam name="filePath" value="<%=stdoutPath%>"/>
                    </ui:actionlink>
<%
        } else {
%>
                    <ui:text value="<%=stdoutPath%>"/>
<%
        }
    } else { %>
                    <ui:text value=""/>
<%  } %>
                </ui:tablecell>
                <ui:tablecell>
<%
    FileLocation stderr = job.getStderrLocation();
    if (stderr != null) {
        String stderrPath = stderr.getFilePath();
        if (!stderrPath.startsWith("/dev")) {
        String stderrHost = stderr.getHost(); %>
                    <ui:actionlink action="doDownloadFile" value="stderr">
                        <ui:actionparam name="fileHost" value="<%=stderrHost%>"/>
                        <ui:actionparam name="filePath" value="<%=stderrPath%>"/>
                    </ui:actionlink>
<%
        } else {
%>
                    <ui:text value="<%=stderrPath%>"/>
<%
        }
    } else { %>
                    <ui:text value=""/>
<%  } %>
                </ui:tablecell>
            </ui:tablerow>
<%
    }
%>
    </ui:table>
</oscache:cache>
