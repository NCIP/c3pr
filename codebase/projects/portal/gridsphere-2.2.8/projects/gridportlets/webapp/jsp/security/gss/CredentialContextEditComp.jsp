<%@ page import="org.gridlab.gridsphere.services.ui.security.gss.CredentialContextViewComp"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<% String credentialDn = (String)CredentialContextViewComp.getPageAttribute(request, "credentialDn", ""); %>
<ui:panel>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text>
                   This credential will be retrieved from  <ui:text style="bold" beanId="credentialRepositoryText"/>.
                </ui:text>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
    <ui:frame beanId="messageFrame"/>
<%@ include file="CredentialEditFrame.jsp" %>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doCredentialEditApply" value="Apply">
                    <ui:actionparam name="credentialDnParam" value="<%=credentialDn%>"/>
                </ui:actionsubmit>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doCancel" value="Cancel">
                    <ui:actionparam name="credentialDnParam" value="<%=credentialDn%>"/>
                </ui:actionsubmit>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:panel>
