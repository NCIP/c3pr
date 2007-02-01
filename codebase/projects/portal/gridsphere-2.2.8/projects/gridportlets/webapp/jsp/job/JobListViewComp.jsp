<%@ page import="org.gridlab.gridsphere.services.resource.Resource,
                 org.gridlab.gridsphere.services.job.Job,
                 org.gridlab.gridsphere.services.job.JobSpec,
                 org.gridlab.gridsphere.services.task.TaskStatus,
                 java.util.Iterator,
                 java.util.List,
                 java.util.ArrayList,
				 java.util.Date,
                 org.gridlab.gridsphere.services.ui.job.JobListViewComp,
                 org.gridlab.gridsphere.services.core.utils.DateUtil,
                 java.text.DateFormat,
                 org.gridlab.gridsphere.services.ui.job.JobListViewComp,
                 org.gridlab.gridsphere.services.ui.job.JobComponent"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<% List jobList = (List)JobListViewComp.getPageAttribute(request, "jobList", new ArrayList()); %>
<%--<ui:form>--%>
<%--    <ui:messagebox beanId="messageBox"/>--%>
    <ui:table width="100%">
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="<%=JobListViewComp.JOB_LIST_REFRESH_ACTION%>" value="Refresh List"/>
                &nbsp;&nbsp;
                <ui:actionsubmit action="<%=JobListViewComp.JOB_SUBMIT_NEW_ACTION%>" value="New Job"/>
<% if (jobList.size() > 0) { %>
                &nbsp;&nbsp;
                <ui:actionsubmit action="<%=JobListViewComp.JOB_LIST_DELETE_ACTION%>" value="Delete Jobs"/>
<% } %>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
    <ui:table sortable="true" width="100%">
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text value="&nbsp;"/>
            </ui:tablecell>
            <ui:tablecell width="100">
                <ui:text value="Job Id"/>
            </ui:tablecell>
            <ui:tablecell width="100">
                <ui:text value="Description"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Job Type"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Resource"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Status"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Date Submitted"/>
            </ui:tablecell>
        </ui:tablerow>
<%  Iterator jobIterator = jobList.iterator();
    while (jobIterator.hasNext()) {
        Job job = (Job)jobIterator.next();
        String jobOid = job.getOid();
        String jobId = job.getJobId();
        try {
            JobSpec jobSpec = job.getJobSpec();
            String jobType = JobComponent.getJobProfile(job).getDescription();
            //String executable = jobSpec.getExecutableLocation().getPath();
            StringBuffer resourceText = new StringBuffer();
            String hostName = job.getHostName();
            if (hostName != null) {
                resourceText.append(hostName);
                String schedulerName = jobSpec.getJobSchedulerName();
                if (schedulerName != null) {
                    resourceText.append("/");
                    resourceText.append(schedulerName);
                }
                String queueName = jobSpec.getJobQueueName();
                if (queueName != null) {
                    resourceText.append("/");
                    resourceText.append(queueName);
                }
            }
            String statusMessage = job.getTaskStatusMessage();
            String jobStatus = null;
            if (statusMessage != null) {
                jobStatus = statusMessage;
            } else {
                jobStatus = job.getJobStatus().getName();
            }
            String timeSubmitted = "&nbsp";
            Date dateSubmitted = job.getDateTaskSubmitted();
            if (dateSubmitted != null) {
                timeSubmitted = DateUtil.getLocalizedDate(job.getUser(),
                                                          request.getLocale(),
                                                          dateSubmitted.getTime(),
                                                          DateFormat.MEDIUM,
                                                          DateFormat.MEDIUM);
            }
            String jobDescription = job.getDescription();
            if (jobDescription == null) jobDescription = "";
    %>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:checkbox beanId="jobOidCheckBox" value="<%=jobOid%>"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:actionlink action="doJobView" value="<%=jobId%>">
                        <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                    </ui:actionlink>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text value="<%=jobDescription%>"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text value="<%=jobType%>"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text value="<%=resourceText.toString()%>"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text value="<%=jobStatus%>"/>
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
