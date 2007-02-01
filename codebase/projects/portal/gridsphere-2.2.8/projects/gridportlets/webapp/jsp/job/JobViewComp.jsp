<%@ page import="org.gridlab.gridsphere.services.ui.job.JobSubmitWizard,
                 org.gridlab.gridsphere.services.ui.job.JobViewComp,
                 org.gridlab.gridsphere.services.ui.job.JobComponent,
                 org.gridlab.gridsphere.services.ui.ActionComponent,
                 org.gridlab.gridsphere.services.job.JobType,
                 org.gridlab.gridsphere.services.ui.job.JobComponent"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<%
    String jobOid = (String)JobComponent.getPageAttribute(request, "jobOid", "");
    Boolean migrateFlag = (Boolean)JobComponent.getPageAttribute(request, "migrateFlag", "");
    Boolean cancelFlag = (Boolean)JobComponent.getPageAttribute(request, "cancelFlag", "");
%>
<%--<ui:form>--%>
<%--    <ui:messagebox beanId="messageBox"/>--%>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doJobListView" value="&lt;&lt;List Jobs"/>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doJobRefresh" value="Refresh View">
                    <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                </ui:actionsubmit>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doJobNew" value="New Job"/>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doJobStage" value="Copy Job">
                    <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                </ui:actionsubmit>
                &nbsp;&nbsp;
<% if (cancelFlag.booleanValue()) { %>
                <ui:actionsubmit action="doJobCancel" value="Stop Job">
                    <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                </ui:actionsubmit>
                &nbsp;&nbsp;
<% } %>
<% if (migrateFlag.booleanValue()) { %>
                <ui:actionsubmit action="doJobMigrate" value="Migrate Job">
                    <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                </ui:actionsubmit>
                &nbsp;&nbsp;
<% } %>
                <ui:actionsubmit action="doJobDelete" value="Delete Job">
                    <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                </ui:actionsubmit>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
    <ui:actioncomponent beanId="jobViewBean"/>
<%--</ui:form>--%>
