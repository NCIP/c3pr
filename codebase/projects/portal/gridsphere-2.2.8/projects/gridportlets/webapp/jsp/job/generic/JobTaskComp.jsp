<%@ page import="java.util.List,
                 java.util.Iterator,
                 org.gridlab.gridsphere.services.task.TaskMetric,
                 java.util.Vector,
                 org.gridlab.gridsphere.services.ui.job.generic.JobTaskViewComp"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<%--<ui:messagebox beanId="messageBox"/>--%>
<h3><ui:text style="nostyle" value="Generic Application"/></h3>
<ui:group>

    <ui:table width="100%">
<!--
        <ui:tablerow header="true">

            <ui:tablecell colspan="3" valign="top" width="100%">
                <ui:text style="nostyle">Generic Application</ui:text>
            </ui:tablecell>

        </ui:tablerow>
-->
        <ui:tablerow>

            <ui:tablecell valign="top">

                <ui:table width="100%">
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:text><b>Job Id</b></ui:text>
                        </ui:tablecell>
                        <ui:tablecell width="*">
                            <ui:text beanId="jobIdText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:text><b>Job Description</b></ui:text>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="jobDescriptionText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:text><b>Job Status</b></ui:text>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="jobStatusText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                </ui:table>

            </ui:tablecell>

            <ui:tablecell valign="top">

                <ui:table width="100%">
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:text><b>Job Resource</b></ui:text>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="jobHostNameText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:text><b>Job Scheduler</b></ui:text>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="jobSchedulerNameText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:text><b>Job Queue</b></ui:text>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="jobQueueNameText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                </ui:table>

            </ui:tablecell>

            <ui:tablecell valign="top">

                <ui:table width="100%">
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:text><b>Date Submitted</b></ui:text>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="dateSubmittedText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:text><b>Last Changed</b></ui:text>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="dateStatusChangedText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:text><b>Date Ended</b></ui:text>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="dateEndedText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                </ui:table>

<%--
                <% List jobMetricList = (List)JobTaskViewComp.getPageAttribute(request, "jobMetricList"); %>
                <ui:text><h4>Job Metrics</h4></ui:text>
                <hr></hr>
                <ui:table zebra="true" sortable="true" width="100%">
                    <ui:tablerow header="true">
                        <ui:tablecell width="150">
                            <ui:text value="Metric"/>
                        </ui:tablecell>
                        <ui:tablecell width="*">
                            <ui:text value="Value"/>
                        </ui:tablecell>
                    </ui:tablerow>
                <%  Iterator jobMetricIterator = jobMetricList.iterator();
                    while (jobMetricIterator.hasNext()) {
                        TaskMetric jobMetric = (TaskMetric)jobMetricIterator.next();
                        String metricName = jobMetric.getMetricName();
                        String metricValue = jobMetric.getValueAsString();
                %>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:text value="<%=metricName%>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%=metricValue%>"/>
                        </ui:tablecell>
                    </ui:tablerow>
                <% } %>
                </ui:table>
--%>

            </ui:tablecell>

        </ui:tablerow>

    </ui:table>
</ui:group>

