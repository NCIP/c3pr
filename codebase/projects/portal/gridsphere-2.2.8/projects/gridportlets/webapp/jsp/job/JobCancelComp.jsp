<%@ page import="org.gridlab.gridsphere.services.ui.job.JobComponent,
                 org.gridlab.gridsphere.services.ui.job.JobComponent"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<%
    String jobOid = (String)JobComponent.getPageAttribute(request, "jobOid");
    Boolean canceledFlag = (Boolean)JobComponent.getPageAttribute(request, "canceledFlag", Boolean.FALSE);
%>
<%--<ui:form>--%>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
<% if (canceledFlag.booleanValue()) { %>
                <ui:actionsubmit action="doJobListView" value="&lt;&lt;List Jobs"/>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doJobRefresh" value="Refresh View">
                    <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                </ui:actionsubmit>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doJobSubmitNew" value="New Job"/>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doJobStage" value="Copy Job">
                    <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                </ui:actionsubmit>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doJobDelete" value="Delete Job">
                    <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                </ui:actionsubmit>
<% } else { %>
                <ui:actionsubmit action="doApply" value="Stop Job">
                    <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                </ui:actionsubmit>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doCancel" value="&lt;&lt;Back">
                    <ui:actionparam name="jobOidParam" value="<%=jobOid%>"/>
                </ui:actionsubmit>
<% } %>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
<%--    <ui:messagebox beanId="messageBox"/>--%>
<%--    <br>--%>
    <ui:actioncomponent beanId="jobViewBean"/>
<%--</ui:form>--%>
