<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.services.job.JobQueue,
                 java.util.List,
                 java.util.Vector,
                 org.gridlab.gridsphere.services.ui.job.generic.GenericResourceSpecEditPage,
                 org.gridlab.gridsphere.services.job.JobScheduler,
                 org.gridlab.gridsphere.services.ui.job.JobComponent,
                 org.gridlab.gridsphere.services.job.JobType,
                 org.gridlab.gridsphere.services.ui.job.JobComponent,
                 org.gridlab.gridsphere.services.ui.job.generic.GenericResourceSpecEditPage"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<%
    JobType jobType = (JobType)JobComponent.getPageAttribute(request, "jobType", JobType.INSTANCE);
    List jobQueueList
          = (List)GenericResourceSpecEditPage.getPageAttribute(request, "jobQueueList", new Vector());
    Boolean useResourceDiscovery
          = (Boolean)GenericResourceSpecEditPage.getPageAttribute(request, "useResourceDiscovery", Boolean.FALSE);
    Boolean isReadOnly = (Boolean)JobComponent.getPageAttribute(request, "isReadOnly", Boolean.FALSE);
%>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text>
                    <b>Step 2.</b> Specfiy your resource requirements for this job.
                </ui:text>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
<%--    <ui:messagebox beanId="messageBox"/>--%>
<%--    <br>--%>
    <ui:group label="Resource Requirements">
    <ui:table>
        <ui:tablerow>
            <ui:tablecell align="left" valign="top">
                <ui:table zebra="false">
                    <ui:tablerow>
                        <ui:tablecell align="left" valign="top">
                            <ui:table>
                                <ui:tablerow>
                                    <ui:tablecell>
                                        <b><ui:text value="Number Of CPUs" style="nostyle"/></b>
                                    </ui:tablecell>
                                    <ui:tablecell>
                                        <ui:textfield beanId="cpuCountField" size="9"/>
                                    </ui:tablecell>
                                </ui:tablerow>
                                <ui:tablerow>
                                    <ui:tablecell>
                                       <b><ui:text value="Minimum Memory" style="nostyle"/></b>
                                    </ui:tablecell>
                                    <ui:tablecell>
                                        <ui:textfield beanId="minMemoryField" size="9"/>
                                    </ui:tablecell>
                                </ui:tablerow>
                            </ui:table>
                        </ui:tablecell>
                    </ui:tablerow>
                </ui:table>
            </ui:tablecell>
            <ui:tablecell align="left" valign="top">
                <ui:table>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:listbox beanId="jobResourceList" size="10"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <%  if (!isReadOnly.booleanValue()) { %>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:actionsubmit action="doViewResource" value="Refresh View"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <%  } %>
                </ui:table>
            </ui:tablecell>
            <ui:tablecell align="left" valign="top">
            <% if (useResourceDiscovery.booleanValue()) { %>
                <ui:table zebra="false">
                    <ui:tablerow>
                        <ui:tablecell width="100%">
                You have selected to allow the portal to discover the appropriate
                resource to which to submit this job based on your resource
                requirements. If you would like to specify the resource manually,
                select a resource from one of the available resources at left
                and click <b>Resource View</b> to select which job queue to
                submit this job.
                        </ui:tablecell>
                    </ui:tablerow>
                </ui:table>
            <% } else if (jobQueueList.size() == 0) { %>
                <ui:table zebra="false">
                    <ui:tablerow>
                        <ui:tablecell width="100%">
                            <ui:text>
               Unfortunately, we are unable to detect the job schedulers and job queues
               on <b><ui:text beanId="jobResourceText" style="nostyle"/></b>. Leave the job scheduler
               and job queue field values set to their default values unless you know
               which values you would like to specify.
                            </ui:text>
                        </ui:tablecell>
                    </ui:tablerow>
                </ui:table>
                <ui:table>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Job Scheduler" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:listbox beanId="jobSchedulerNameList"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Job Queue" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:textfield beanId="jobQueueNameField" size="9"/>
                        </ui:tablecell>
                    </ui:tablerow>
                </ui:table>
            <% } else { %>
                <p>
                <ui:text>
                    The following job queues are available on <b><ui:text style="nostyle" beanId="jobResourceText"/></b>.
                </ui:text>
                </p>
                <ui:table width="100%" sortable="true">
                    <ui:tablerow header="true">
                        <ui:tablecell>
                            &nbsp;
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="Scheduler"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="Queue"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="Num Of Nodes"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="Free Nodes"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="Jobs In Queue"/>
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
<%--
                    <ui:tablerow>
                        <ui:tablecell align="center" colspan="9">
                            <ui:text>
                                The following job queues are available on <b><ui:text beanId="jobResourceText"/></b>.
                            </ui:text>
                        </ui:tablecell>
                    </ui:tablerow>
--%>
            <%      Iterator resourceIterator = jobQueueList.iterator();
                    while (resourceIterator.hasNext()) {
                       JobQueue jobQueue = (JobQueue)resourceIterator.next();
                       JobScheduler jobScheduler = jobQueue.getJobScheduler();
                       String jobSchedulerName = jobScheduler.getJobSchedulerType().getName();
                       String jobQueueName = jobQueue.getName();
                       String jobQueueOptionValue = jobSchedulerName + ":" + jobQueueName;
                       int numNodes = jobQueue.getMaxNumNodes();
                       int numFreeNodes = jobQueue.getNumFreeNodes();
                       int numLiveJobs = jobQueue.getNumLiveJobs();
                       int numActiveJobs = jobQueue.getNumActiveJobs();
                       int numPendingJobs = jobQueue.getNumPendingJobs();
                       int numSuspendedJobs = jobQueue.getNumSuspendedJobs();
            %>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:radiobutton beanId="jobQueueOption" value="<%=jobQueueOptionValue%>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%=jobSchedulerName%>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%=jobQueueName%>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%=(numNodes <= 0 ? "-" : String.valueOf(numNodes))%>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%=(numFreeNodes <= 0 ? "-" : String.valueOf(numFreeNodes))%>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%=(numLiveJobs < 0 ? "-" : String.valueOf(numLiveJobs))%>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%=(numActiveJobs < 0 ? "-" : String.valueOf(numActiveJobs))%>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%=(numPendingJobs < 0 ? "-" : String.valueOf(numPendingJobs))%>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%=(numSuspendedJobs < 0 ? "-" : String.valueOf(numSuspendedJobs))%>"/>
                        </ui:tablecell>
                    </ui:tablerow>
            <%
                    }
            %>
                </ui:table>
            <% } %>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
    </ui:group>