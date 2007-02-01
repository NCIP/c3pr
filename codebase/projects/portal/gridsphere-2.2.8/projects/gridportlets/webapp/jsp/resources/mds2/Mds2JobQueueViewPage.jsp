<%@ page import="org.gridlab.gridsphere.services.job.Job,
               org.gridlab.gridsphere.services.job.JobSpec,
               org.gridlab.gridsphere.services.task.TaskStatus,
               java.util.Iterator,
               java.util.List,
               java.util.ArrayList,
               org.gridlab.gridsphere.services.ui.ActionComponent,
               org.gridlab.gridsphere.services.ui.resource.browser.ResourceComponent,
               org.gridlab.gridsphere.portlet.impl.SportletProperties,
               org.gridlab.gridsphere.services.file.FileLocation,
               org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory,
               org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory,
               org.gridlab.gridsphere.services.core.utils.DateUtil,
               java.text.DateFormat,
               org.gridlab.gridsphere.services.job.JobScheduler,
                 org.gridlab.gridsphere.services.job.JobQueue"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="/oscache" prefix="oscache" %>
<portlet:defineObjects/>
<%  String compId = (String) request.getAttribute(SportletProperties.GP_COMPONENT_ID);
   String key = compId + "mds2JobQueueView_" + (String)ResourceComponent.getPageAttribute(request, "resourceOid", "0");
  Boolean refreshContent = (Boolean)ResourceComponent.getPageAttribute(request, "refreshContent", Boolean.FALSE); %>
<oscache:cache key="<%=key%>" refresh="<%=refreshContent.booleanValue()%>">
<%
  JobQueue jobQueue = (JobQueue)ResourceComponent.getPageAttribute(request, "resource");
%>
  <h3>Job Queue</h3>
<%
  String queueName = jobQueue.getName();
  String description = jobQueue.getDescription();
  if (description == null) description = "";
  JobScheduler jobScheduler = jobQueue.getJobScheduler();
  String schedulerName = jobScheduler.getJobSchedulerType().getName();
  String hostName = jobScheduler.getParentResource().getLabel();
  String dispatchType = jobQueue.getDispatchType();
  String status = jobQueue.getStatus();

  int numNodes = jobQueue.getMaxNumNodes();
  int numFreeNodes = jobQueue.getNumFreeNodes();

  int memorySize = jobQueue.getMemorySizeInMB();
  String maxCpuTime = jobQueue.getMaxCpuTime();
  String maxTime = jobQueue.getMaxTime();

  int numLiveJobs = jobQueue.getNumLiveJobs();
  int numActiveJobs = jobQueue.getNumActiveJobs();
  int numPendingJobs = jobQueue.getNumPendingJobs();
  int numSuspendedJobs = jobQueue.getNumSuspendedJobs();

%>
  <ui:table width="100%">
      <ui:tablerow>
          <ui:tablecell width="120">
              <b><ui:text style="nostyle" value="Queue Name"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value="<%=queueName%>"/>
          </ui:tablecell>
      </ui:tablerow>
      <ui:tablerow>
          <ui:tablecell>
              <b><ui:text style="nostyle" value="Description"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value="<%=description%>"/>
          </ui:tablecell>
      </ui:tablerow>
      <ui:tablerow>
          <ui:tablecell width="50">
              <b><ui:text style="nostyle" value="Host"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value="<%=hostName%>"/>
          </ui:tablecell>
      </ui:tablerow>
      <ui:tablerow>
          <ui:tablecell width="50">
              <b><ui:text style="nostyle" value="Scheduler"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value="<%=schedulerName%>"/>
          </ui:tablecell>
      </ui:tablerow>
      <ui:tablerow>
          <ui:tablecell width="50">
              <b><ui:text style="nostyle" value="Dispatch Type"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value="<%=dispatchType%>"/>
          </ui:tablecell>
      </ui:tablerow>
      <ui:tablerow>
          <ui:tablecell width="50">
              <b><ui:text style="nostyle" value="Status"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value="<%=status%>"/>
          </ui:tablecell>
      </ui:tablerow>

      <ui:tablerow>
          <ui:tablecell width="50">
              <b><ui:text style="nostyle" value="Num of Nodes"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value='<%=(numNodes <= 0 ? "-" : String.valueOf(numNodes))%>'/>
          </ui:tablecell>
      </ui:tablerow>
      <ui:tablerow>
          <ui:tablecell width="50">
              <b><ui:text style="nostyle" value="Free Nodes"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value='<%=(numFreeNodes <= 0 ? "-" : String.valueOf(numFreeNodes))%>'/>
          </ui:tablecell>
      </ui:tablerow>
     <ui:tablerow>
          <ui:tablecell width="50">
              <b><ui:text style="nostyle" value="Memory Size"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value='<%=(memorySize <= 0 ? "- (MB)" : String.valueOf(memorySize)) + "(MB)"%>'/>
          </ui:tablecell>
      </ui:tablerow>
      <ui:tablerow>
          <ui:tablecell width="50">
              <b><ui:text style="nostyle" value="Max Time"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value="<%=maxTime%>"/>
          </ui:tablecell>
      </ui:tablerow>
      <ui:tablerow>
          <ui:tablecell width="50">
              <b><ui:text style="nostyle" value="Max CPU Time"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value="<%=maxCpuTime%>"/>
          </ui:tablecell>
      </ui:tablerow>
      <ui:tablerow>
          <ui:tablecell width="50">
              <b><ui:text style="nostyle" value="Total Jobs"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value='<%=(numLiveJobs <= 0 ? "-" : String.valueOf(numLiveJobs))%>'/>
          </ui:tablecell>
      </ui:tablerow>
      <ui:tablerow>
          <ui:tablecell width="50">
              <b><ui:text style="nostyle" value="Active Jobs"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value='<%=(numActiveJobs <= 0 ? "-" : String.valueOf(numActiveJobs))%>'/>
          </ui:tablecell>
      </ui:tablerow>
      <ui:tablerow>
          <ui:tablecell width="50">
              <b><ui:text style="nostyle" value="Pending Jobs"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value='<%=(numPendingJobs <= 0 ? "-" : String.valueOf(numPendingJobs))%>'/>
          </ui:tablecell>
      </ui:tablerow>
      <ui:tablerow>
          <ui:tablecell width="50">
              <b><ui:text style="nostyle" value="Suspended Jobs"/></b>
          </ui:tablecell>
          <ui:tablecell>
              <ui:text value='<%=(numSuspendedJobs <= 0 ? "-" : String.valueOf(numSuspendedJobs))%>'/>
          </ui:tablecell>
      </ui:tablerow>

  </ui:table>
</oscache:cache>
