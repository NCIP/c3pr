<%@ page import="org.gridlab.gridsphere.services.resource.Resource,
                 org.gridlab.gridsphere.services.job.Job,
                 org.gridlab.gridsphere.services.job.JobSpec,
                 org.gridlab.gridsphere.services.task.TaskStatus,
                 java.util.Iterator,
                 java.util.List,
				 java.util.Date,
                 org.gridlab.gridsphere.services.ui.job.JobComponent,
                 org.gridlab.gridsphere.services.ui.job.JobComponent,
                 org.gridlab.gridsphere.services.ui.job.JobProfile"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<% List jobList = (List)JobComponent.getPageAttribute(request, "jobList"); %>
<% Boolean deletedFlag = (Boolean)JobComponent.getPageAttribute(request, "deletedFlag", Boolean.FALSE); %>
<%--<ui:form>--%>
<%--    <ui:messagebox beanId="messageBox"/>--%>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
<% if (deletedFlag.booleanValue()) { %>
                <ui:actionsubmit action="doJobListView" value="&lt;&lt;List Jobs"/>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doJobSubmitNew" value="New Job"/>
<% } else { %>
                <ui:actionsubmit action="doApply" value="Delete Jobs"/>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doCancel" value="Cancel Deletion"/>
<% } %>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
    <ui:table>
        <ui:tablerow header="true">
            <ui:tablecell width="100">
                <ui:text value="Job Id"/>
            </ui:tablecell>
            <ui:tablecell width="50">
                <ui:text value="Job Type"/>
            </ui:tablecell>
            <ui:tablecell width="50">
                <ui:text value="Status"/>
            </ui:tablecell>
            <ui:tablecell width="50">
                <ui:text value="Description"/>
            </ui:tablecell>
            <ui:tablecell width="50">
                <ui:text value="Resource"/>
            </ui:tablecell>
            <ui:tablecell width="50">
                <ui:text value="Scheduler"/>
            </ui:tablecell>
            <ui:tablecell width="50">
                <ui:text value="Queue"/>
            </ui:tablecell>
            <ui:tablecell width="150">
                <ui:text value="Date Submitted"/>
            </ui:tablecell>
        </ui:tablerow>
<%  Iterator jobIterator = jobList.iterator();
    while (jobIterator.hasNext()) {
        Job job = (Job)jobIterator.next();
        String jobId = job.getJobId();
        try {
            JobSpec jobSpec = job.getJobSpec();
            String jobType = JobComponent.getJobProfile(job).getDescription();
            String jobStatus = job.getJobStatus().getName();
            //String executable = jobSpec.getExecutableLocation().getPath();
            String hostName = jobSpec.getHostName();
            String schedulerName = jobSpec.getJobSchedulerName();
            String queueName = jobSpec.getJobQueueName();
            String statusMessage = job.getTaskStatusMessage();
            if (statusMessage != null) {
                jobStatus += " : " + statusMessage;
            }
            String timeSubmitted = "&nbsp";
            Date dateSubmitted = job.getDateTaskSubmitted();
            if (dateSubmitted != null) timeSubmitted = dateSubmitted.toString();
            String jobDescription = job.getDescription();
            if (jobDescription == null) jobDescription = "";
    %>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text value="<%=jobId%>"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text value="<%=jobType%>"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text value="<%=jobStatus%>"/>
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
                    <ui:text value="<%=queueName%>"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text value="<%=timeSubmitted%>"/>
                </ui:tablecell>
            </ui:tablerow>
<%      } catch(Exception e) { %>

<%
        }
    }
%>
    </ui:table>
<%--</ui:form>--%>
