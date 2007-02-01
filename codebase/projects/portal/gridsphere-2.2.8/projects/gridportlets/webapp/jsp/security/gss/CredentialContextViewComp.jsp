<%@ page import="org.gridlab.gridsphere.services.ui.security.gss.CredentialContextViewComp"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<%
    String credentialDn = (String)CredentialContextViewComp.getPageAttribute(request, "credentialDn", "");
    Boolean isActive = (Boolean)CredentialContextViewComp.getPageAttribute(request, "isActive", Boolean.FALSE);
%>
<ui:panel>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doCredentialList" value="List Credentials"/>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doCredentialView" value="Refresh View">
                    <ui:actionparam name="credentialDnParam" value="<%=credentialDn%>"/>
                </ui:actionsubmit>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doCredentialEdit" value="Edit Credential">
                    <ui:actionparam name="credentialDnParam" value="<%=credentialDn%>"/>
                </ui:actionsubmit>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doCredentialDeactivate" value="Deactivate Credential"
                                 disabled="<%=!isActive.booleanValue()%>">
                    <ui:actionparam name="credentialDnParam" value="<%=credentialDn%>"/>
                </ui:actionsubmit>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doCredentialDelete" value="Delete Credential">
                    <ui:actionparam name="credentialDnParam" value="<%=credentialDn%>"/>
                </ui:actionsubmit>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
    <ui:frame beanId="messageFrame"/>
</ui:panel>
<%@ include file="CredentialInfoPanel.jsp" %>
<ui:panel>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="Passphrase:"></ui:text>
                &nbsp;
                <ui:password beanId="passphrase" size="20"/>
                &nbsp;
                <ui:actionsubmit action="doCredentialRetrieve" value="Retrieve Credential">
                    <ui:actionparam name="credentialDnParam" value="<%=credentialDn%>"/>
                </ui:actionsubmit>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:panel>
<%@ include file="CredentialStatusPanel.jsp" %>
