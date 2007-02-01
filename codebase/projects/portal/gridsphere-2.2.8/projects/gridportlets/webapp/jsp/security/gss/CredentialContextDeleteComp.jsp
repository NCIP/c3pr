<%@ page import="org.gridlab.gridsphere.services.ui.security.gss.CredentialContextViewComp"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<% String credentialDn = (String)CredentialContextViewComp.getPageAttribute(request, "credentialDn", ""); %>
<% Boolean deletedFlag = (Boolean)CredentialContextViewComp.getPageAttribute(request, "deletedFlag", Boolean.FALSE); %>
<ui:panel>
<% if (deletedFlag.booleanValue()) { %>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doCredentialList" value="List Credentials"/>
                &nbsp;&nbsp;
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text>
                   This credential will no longer be retrieved from <ui:text style="bold" beanId="credentialRepositoryText"/>.
                </ui:text>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
<% } else { %>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doApply" value="Confirm Delete">
                    <ui:actionparam name="credentialDnParam" value="<%=credentialDn%>"/>
                </ui:actionsubmit>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doCancel" value="&lt;&lt;Back">
                    <ui:actionparam name="credentialDnParam" value="<%=credentialDn%>"/>
                </ui:actionsubmit>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text>
                   To delete this credential click the <bold>Confirm Delete</bold> button.
                </ui:text>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
<% } %>
</ui:panel>
<%@ include file="CredentialInfoPanel.jsp" %>
