<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.services.resource.HardwareResource,
                 java.util.List,
                 org.gridlab.gridsphere.services.resource.ServiceResource,
                 org.gridlab.gridsphere.services.resource.SoftwareResource,
                 org.gridlab.gridsphere.services.ui.resource.browser.ResourceComponent,
                 org.gridlab.gridsphere.portlet.impl.SportletProperties,
                 org.gridlab.gridsphere.services.ui.resource.browser.BaseResourcePage"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="/oscache" prefix="oscache" %>
<portlet:defineObjects/>
<% String compId = (String) request.getAttribute(SportletProperties.GP_COMPONENT_ID);
   String key = compId + "generalSoftwareResourceListView";
   Boolean refreshContent = (Boolean)ResourceComponent.getPageAttribute(request, "refreshContent", Boolean.FALSE); %>
<oscache:cache key="<%=key%>" refresh="<%=refreshContent.booleanValue()%>">
<%
    List resourceList = (List)ResourceComponent.getPageAttribute(request, "softwareResourceList");
    String lastUpdated = (String)ResourceComponent.getPageAttribute(request, BaseResourcePage.LAST_UPDATED, "");
%>
    <h3>Software List <%=lastUpdated%></h3>
    <ui:table width="100%" sortable="true" zebra="true">
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text value="Software"/>
            </ui:tablecell>
        </ui:tablerow>
<%  Iterator resourceIterator = resourceList.iterator();
    while (resourceIterator.hasNext()) {
        SoftwareResource resource = (SoftwareResource)resourceIterator.next();
        String dn = resource.getDn();
        String label = resource.getLabel();
%>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionlink action="doHardwareSoftwareView" value="<%=label%>">
                    <ui:actionparam name="<%=ResourceComponent.RESOURCE_DN_PARAM%>" value="<%=dn%>"/>
                </ui:actionlink>
            </ui:tablecell>
        </ui:tablerow>
<%
    }
%>
    </ui:table>
</oscache:cache>
