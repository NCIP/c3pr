<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.services.resource.HardwareResource,
                 java.util.List,
                 org.gridlab.gridsphere.services.resource.ResourceAttribute,
                 org.gridlab.gridsphere.services.resources.gridssh.GridSshResourceType,
                 java.util.ArrayList,
                 org.gridlab.gridsphere.services.ui.resource.browser.ResourceComponent,
                 org.gridlab.gridsphere.portlet.impl.SportletProperties,
                 org.gridlab.gridsphere.services.ui.resource.browser.BaseResourcePage"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="/oscache" prefix="oscache" %>
<portlet:defineObjects/>
<% String compId = (String) request.getAttribute(SportletProperties.GP_COMPONENT_ID);
   String key = compId + "generalHardwareResourceListView";
   Boolean refreshContent = (Boolean)ResourceComponent.getPageAttribute(request, "refreshContent", Boolean.FALSE); %>
<oscache:cache key="<%=key%>" refresh="<%=refreshContent.booleanValue()%>">
<%
    List resourceList = (List)ResourceComponent.getPageAttribute(request, "resourceList", new ArrayList());
    String lastUpdated = (String)ResourceComponent.getPageAttribute(request, BaseResourcePage.LAST_UPDATED, "");
%>
    <h3>Hardware List <%=lastUpdated%></h3>
    <ui:table zebra="true" sortable="true" width="100%">
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text style="nostyle" value="Resource"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text style="nostyle" value="Hostname"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text style="nostyle" value="Description"/>
            </ui:tablecell>
        </ui:tablerow>
<%  Iterator hardwareResourceIterator = resourceList.iterator();
    while (hardwareResourceIterator.hasNext()) {
        HardwareResource resource = (HardwareResource)hardwareResourceIterator.next();
        String dn = resource.getDn();
        String hostName = resource.getHostName();
        String label = resource.getLabel();
        String description = resource.getDescription();

        // Has ssh
//        boolean hasSsh = (resource.hasChildResources(GridSshResourceType.INSTANCE));
%>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionlink action="doResourceView" value="<%=label%>">
                    <ui:actionparam name="<%=ResourceComponent.RESOURCE_DN_PARAM%>" value="<%=dn%>"/>
                </ui:actionlink>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=hostName%>"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=description%>" style="nostyle"/>
            </ui:tablecell>
        </ui:tablerow>
<%
    }
%>
    </ui:table>
</oscache:cache>
