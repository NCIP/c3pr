<%@ page import="java.util.Iterator,
                 java.util.List,
                 org.gridlab.gridsphere.services.resource.ServiceResource,
                 java.util.Vector,
                 org.gridlab.gridsphere.services.ui.resource.browser.ResourceComponent,
                 org.gridlab.gridsphere.portlet.impl.SportletProperties,
                 org.gridlab.gridsphere.services.ui.resource.browser.BaseResourcePage"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="/oscache" prefix="oscache" %>
<portlet:defineObjects/>
<% String compId = (String) request.getAttribute(SportletProperties.GP_COMPONENT_ID);
   String key = compId + "generalServiceResourceListView";
   Boolean refreshContent = (Boolean)ResourceComponent.getPageAttribute(request, "refreshContent", Boolean.FALSE); %>
<oscache:cache key="<%=key%>" refresh="<%=refreshContent.booleanValue()%>">
<%
    List resourceList = (List)ResourceComponent.getPageAttribute(request, "resourceList", new Vector());
    String lastUpdated = (String)ResourceComponent.getPageAttribute(request, BaseResourcePage.LAST_UPDATED, "");
%>
    <h3>Service List <%=lastUpdated%></h3>
    <ui:table width="100%" sortable="true" zebra="true">
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text value="Resource"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Service"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Protocol"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Port"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Service Path"/>
            </ui:tablecell>
<%--
            <ui:tablecell width="100">
                <ui:text value="Available"/>
            </ui:tablecell>
--%>
        </ui:tablerow>
<%  Iterator resourceIterator = resourceList.iterator();
    while (resourceIterator.hasNext()) {
        ServiceResource resource = (ServiceResource)resourceIterator.next();
        String dn = resource.getDn();
        String host = resource.getHardwareResource().getLabel();
        String service = resource.getResourceType().getLabel(resource, request.getLocale());
        String protocol = resource.getProtocol();
        String port = resource.getPort();
        String path = resource.getServicePath();
        boolean isAvailable = resource.isAvailable();

/*
        String status = null;
        if (isAvailable) {
            status = "Yes";
        } else {
            status = "<font color='DARKRED'>No</font>";
        }
*/
%>
        <ui:tablerow>
<%--
            <ui:tablecell>
                <ui:actionlink action="doResourceView" value="<%=label%>">
                    <ui:actionparam name="<%=ResourceComponent.RESOURCE_DN_PARAM%>" value="<%=dn%>"/>
                </ui:actionlink>
            </ui:tablecell>
--%>
            <ui:tablecell>
                <ui:text value="<%=host%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=service%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=protocol%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=port%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=path%>"/>
            </ui:tablecell>
<%--
            <ui:tablecell>
                <ui:text value="<%=status%>" style="nostyle"/>
            </ui:tablecell>
--%>
        </ui:tablerow>
<%
    }
%>
    </ui:table>
</oscache:cache>
