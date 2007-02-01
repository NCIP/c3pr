<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.services.resource.HardwareResource,
                 java.util.List,
                 org.gridlab.gridsphere.services.resource.HardwareAccount,
                 org.gridlab.gridsphere.services.ui.resource.browser.ResourceComponent,
                 java.util.ArrayList,
                 org.gridlab.gridsphere.portlet.impl.SportletProperties,
                 org.gridlab.gridsphere.services.ui.resource.browser.BaseResourcePage"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="/oscache" prefix="oscache" %>
<portlet:defineObjects/>
<% String compId = (String) request.getAttribute(SportletProperties.GP_COMPONENT_ID);
   String key = compId + "generalHardwareAccountListView";
   Boolean refreshContent = (Boolean)ResourceComponent.getPageAttribute(request, "refreshContent", Boolean.FALSE); %>
<oscache:cache key="<%=key%>" refresh="<%=refreshContent.booleanValue()%>">
<%
    List resourceList = (List)ResourceComponent.getPageAttribute(request, "resourceList", new ArrayList());
    String lastUpdated = (String)ResourceComponent.getPageAttribute(request, BaseResourcePage.LAST_UPDATED, "");
%>
    <h3>Hardware Account List <%=lastUpdated%></h3>
    <ui:table width="100%" sortable="true" zebra="true">
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text value="Resource"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Username"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Home Dir"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="User Shell"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="User Dns"/>
            </ui:tablecell>
        </ui:tablerow>
<%  Iterator resourceIterator = resourceList.iterator();
    while (resourceIterator.hasNext()) {
        HardwareAccount resource = (HardwareAccount)resourceIterator.next();
        String parent = resource.getHostResource().getLabel();
        String dn = resource.getDn();
        String userId = resource.getUserId();
        String homeDir = resource.getHomeDir();
        String userShell = resource.getUserShell();
        StringBuffer dnBuffer = new StringBuffer();
        List userDnList = resource.getUserDns();
        if (userDnList == null) {
            System.err.println("User dn list is null!");
        }
        int numUserDn = userDnList.size();
        if (numUserDn >= 1) {
            String userDn = (String)userDnList.get(0);
            dnBuffer.append(userDn);
            for (int ii = 1; ii < userDnList.size(); ++ii) {
                userDn = (String)userDnList.get(ii);
                dnBuffer.append("<br>");
                dnBuffer.append(userDn);
            }
        }
%>
        <ui:tablerow>
<%--
            <ui:tablecell>
                <ui:actionlink action="doHardwareAccountView" value="<%=userId%>">
                    <ui:actionparam name="hardwareAccountOidParam" value="<%=dn%>"/>
                </ui:actionlink>
            </ui:tablecell>
--%>
            <ui:tablecell>
                <ui:text value="<%=parent%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=userId%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=homeDir%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=userShell%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=dnBuffer.toString()%>"/>
            </ui:tablecell>
        </ui:tablerow>
<%
    }
%>
    </ui:table>
</oscache:cache>
