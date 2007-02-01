<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.services.resource.HardwareResource,
                 java.util.List,
                 org.gridlab.gridsphere.services.resource.HardwareAccount,
                 org.gridlab.gridsphere.services.job.JobQueue,
                 org.gridlab.gridsphere.services.ui.resource.browser.ResourceComponent,
                 java.util.ArrayList,
                 org.gridlab.gridsphere.portlet.impl.SportletProperties,
                 org.gridlab.gridsphere.services.job.JobScheduler,
                 org.gridlab.gridsphere.services.ui.resource.browser.BaseResourcePage"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="/oscache" prefix="oscache" %>
<portlet:defineObjects/>
<% String compId = (String) request.getAttribute(SportletProperties.GP_COMPONENT_ID);
   String key = compId + "generalJobQueueListView";
   Boolean refreshContent = (Boolean)ResourceComponent.getPageAttribute(request, "refreshContent", Boolean.FALSE); %>
<oscache:cache key="<%=key%>" refresh="<%=refreshContent.booleanValue()%>">
<%
    List jobQueueList = (List)ResourceComponent.getPageAttribute(request, "resourceList", new ArrayList());
    String lastUpdated = (String)ResourceComponent.getPageAttribute(request, BaseResourcePage.LAST_UPDATED, "");
%>
    <h3>Job Queue List <%=lastUpdated%></h3>
    <ui:table width="100%" sortable="true" zebra="true">
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text value="Queue"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Resource"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Scheduler"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Num Of Nodes"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Free Nodes"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Total Jobs"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Active Jobs"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Pending Jobs"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Suspended Jobs"/>
            </ui:tablecell>
      </ui:tablerow>
<%  Iterator resourceIterator = jobQueueList.iterator();
    while (resourceIterator.hasNext()) {

        JobQueue jobQueue = (JobQueue)resourceIterator.next();
        JobScheduler jobScheduler = jobQueue.getJobScheduler();
        String hostName = jobScheduler.getParentResource().getLabel();
        String schedulerName = jobScheduler.getJobSchedulerType().getName();
        String jobQueueDn = jobQueue.getDn();
        String jobQueueName = jobQueue.getName();
        int numNodes = jobQueue.getMaxNumNodes();
        int numFreeNodes = jobQueue.getNumFreeNodes();
        int numLiveJobs = jobQueue.getNumLiveJobs();
        int numActiveJobs = jobQueue.getNumActiveJobs();
        int numPendingJobs = jobQueue.getNumPendingJobs();
        int numSuspendedJobs = jobQueue.getNumSuspendedJobs();
%>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionlink action="doResourceView" value="<%=jobQueueName%>">
                    <ui:actionparam name="<%=ResourceComponent.RESOURCE_DN_PARAM%>" value="<%=jobQueueDn%>"/>
                </ui:actionlink>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=hostName%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=schedulerName%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=(numNodes <= 0 ? "-" : String.valueOf(numNodes))%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=(numFreeNodes <= 0 ? "-" : String.valueOf(numFreeNodes))%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=(numLiveJobs <= 0 ? "-" : String.valueOf(numLiveJobs))%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=(numActiveJobs <= 0 ? "-" : String.valueOf(numActiveJobs))%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=(numPendingJobs <= 0 ? "-" : String.valueOf(numPendingJobs))%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=(numSuspendedJobs <= 0 ? "-" : String.valueOf(numSuspendedJobs))%>"/>
            </ui:tablecell>
        </ui:tablerow>
<%
    }
%>
    </ui:table>
</oscache:cache>
