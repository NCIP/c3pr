<%@ page import="org.gridlab.gridsphere.services.ui.job.JobComponent,
                 org.gridlab.gridsphere.services.job.JobType"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<% JobType jobType = (JobType)JobComponent.getPageAttribute(request, "jobType", JobType.INSTANCE); %>
    <ui:messagebox beanId="messageBox"/>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text>
                    <b>Step 3.</b> Confirm and submit your job specification.
                    This job will be submitted using the <b><%=jobType.getLabel(request.getLocale())%></b>.
                </ui:text>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
<%--    <ui:messagebox beanId="messageBox"/>--%>
<%--    <br>--%>
    <ui:table width="100%">
        <ui:tablerow>
            <ui:tablecell align="left" valign="top">
<%--                <ui:group label="Application Details">--%>
                <ui:group label="Job Specification">
                <ui:table>
                    <ui:tablerow>
                        <ui:tablecell width="150">
                            <b><ui:text value="Description" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="descriptionText" />
                        </ui:tablecell>
                    </ui:tablerow>
                    <%  if (jobType.getSupportsDirectory()) { %>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Directory" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="directoryText" />
                        </ui:tablecell>
                    </ui:tablerow>
                    <%  } %>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Executable" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="executableText" />
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Method" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="execMethodText"/>&nbsp;
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Arguments" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="argumentsText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Environment" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="environmentText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Stdout" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="stdoutText" />
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Stderr" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="stderrText" />
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Stage-In Files" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="stageInText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Stage-Out Files" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="stageOutText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <% if (jobType.getSupportsCheckPointing()) { %>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Checkpoint Files" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="checkpointText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <% } %>
<%--                </ui:table>--%>
<%--                </ui:group>--%>
<%--            </ui:tablecell>--%>
<%--        </ui:tablerow>--%>
<%--        <ui:tablerow>--%>
<%--            <ui:tablecell align="left" valign="top">--%>
<%--                <ui:group label="Resource Requirements">--%>
<%--                <ui:table>--%>
                    <ui:tablerow>
                        <ui:tablecell width="125">
                            <b><ui:text value="Job Resource" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="jobResourceText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Job Scheduler" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="jobSchedulerNameText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Job Queue" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="jobQueueNameText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                            <b><ui:text value="Minimum CPUs" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="cpuCountText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                    <ui:tablerow>
                        <ui:tablecell>
                           <b><ui:text value="Minimum Memory" style="nostyle"/></b>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text beanId="minMemoryText"/>
                        </ui:tablecell>
                    </ui:tablerow>
                </ui:table>
                </ui:group>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
