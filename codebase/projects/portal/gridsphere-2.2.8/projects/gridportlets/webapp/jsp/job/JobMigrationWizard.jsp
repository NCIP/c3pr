<%@ page import="org.gridlab.gridsphere.services.ui.job.JobMigrateWizard,
                 org.gridlab.gridsphere.services.ui.job.JobMigrateWizard"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<%
    Boolean prevPageFlag = (Boolean)JobMigrateWizard.getPageAttribute(request, "prevPageFlag", Boolean.FALSE);
    Boolean nextPageFlag = (Boolean)JobMigrateWizard.getPageAttribute(request, "nextPageFlag", Boolean.FALSE);
%>
<%--<ui:form>--%>
    <ui:table width="100%">
<%--
        <ui:tablerow>
            <ui:tablecell>
                <ui:text>
<% if (prevPageFlag.booleanValue()) { %>
                Submitting a <b>Generic Application</b> with the <b>Globus Resource Management System</b>.
<% } else { %>
Select the type of job you would like to submit and the service  you would like to use to submit the job.
<% } %>
                </ui:text>
            </ui:tablecell>
        </ui:tablerow>
--%>
        <ui:tablerow>
            <ui:tablecell>
<% if (prevPageFlag.booleanValue()) { %>
                <ui:actionsubmit action="doPreviousPage" value="&lt;&lt;Previous"/>
<% } else { %>
                <ui:actionsubmit action="doPreviousPage" value="&lt;&lt;Previous" disabled="true"/>
<% } %>
                &nbsp;&nbsp;
<% if (nextPageFlag.booleanValue()) { %>
                <ui:actionsubmit action="doNextPage" value="Next&gt;&gt;"/>
                &nbsp;&nbsp;
<% } else { %>
                <ui:actionsubmit action="doSubmitWizard" value="Migrate"/>
                &nbsp;&nbsp;
<% } %>
                <ui:actionsubmit action="doCancelWizard" value="Cancel"/>
            &nbsp;&nbsp;
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
<%--    <br>--%>
<%--    <ui:messagebox beanId="<%=JobMigrateWizard.MESSAGE_BOX_ID%>"/>--%>
    <ui:actioncomponent beanId="<%=JobMigrateWizard.PAGE_BEAN_ID%>"/>
<%--</ui:form>--%>
