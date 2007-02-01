<%@ page import="org.gridlab.gridsphere.services.resource.Resource,
                 java.util.Iterator,
                 java.util.List,
				 java.util.Date,
                 org.gridlab.gridsphere.services.security.gss.CredentialContext,
                 org.gridlab.gridsphere.services.core.utils.DateUtil,
                 org.gridlab.gridsphere.services.security.gss.CredentialUtil,
                 org.gridlab.gridsphere.services.ui.security.gss.CredentialContextListViewComp,
                 java.util.ArrayList"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<% List credentialContextList
        = (List)CredentialContextListViewComp.getPageAttribute(request, "credentialContextList", new ArrayList()); %>
    <ui:table width="100%">
            <ui:tablerow>
                <ui:tablecell>
                    <ui:actionsubmit action="doCredentialList" value="List Credentials"/>
                    &nbsp;&nbsp;
                    <ui:actionsubmit action="doCredentialNew" value="New Credential"/>
                </ui:tablecell>
            </ui:tablerow>
    </ui:table>
    <% if (credentialContextList.size() > 0) { %>
    <ui:table width="100%">
        <ui:tablerow>
            <ui:tablecell>
                <ui:text>
                   The following credentials can be retrieved from <ui:text style="bold" beanId="credentialRepositoryText"/>.
                </ui:text>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
    <ui:table width="100%">
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text style="nostyle" value="Credential"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text style="nostyle" value="Certificate"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text style="nostyle" value="Status"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text style="nostyle" value="Time left"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text style="nostyle" value=""/>
            </ui:tablecell>
        </ui:tablerow>
    <%  Iterator contextIterator = credentialContextList.iterator();
        while (contextIterator.hasNext()) {
            CredentialContext context = (CredentialContext)contextIterator.next();
            String dn = context.getDn();
            String label = context.getLabel();
            boolean isActive = context.isActive();
            String status = null;
            String color = null;
            if (isActive) {
                color = "green";
                status = "Active";
            } else {
                color = "darkred";
                status = "Inactive";
            }
            String timeRemaining = CredentialUtil.getTimeRemainingText(context);

    %>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionlink action="doCredentialView" value="<%=label%>">
                    <ui:actionparam name="credentialDnParam" value="<%=dn%>"/>
                </ui:actionlink>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=dn%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <font color="<%=color%>"><ui:text style="nostyle" value="<%=status%>"/></font>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=timeRemaining%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:actionsubmit action="doCredentialDeactivate" value="Deactivate" disabled="<%=!isActive%>">
                    <ui:actionparam name="credentialDnParam" value="<%=dn%>"/>
                </ui:actionsubmit>
            </ui:tablecell>
        </ui:tablerow>
    <%
        }
    %>
    </ui:table>
    <ui:table width="100%">
        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="Passphrase:"/>
                &nbsp;
                <ui:password beanId="passphrase" size="20"/>
                &nbsp;
                <ui:actionsubmit action="doCredentialListRetrieve" value="Retrieve Credentials"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
    <% } else { %>
    <ui:table width="100%">
        <ui:tablerow>
            <ui:tablecell>
                <ui:text>
                  Please click on <b>New Credential</b> to specify a credential to
                  retrieve from <ui:text style="bold" beanId="credentialRepositoryText"/>.
                </ui:text>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
    <% } %>
