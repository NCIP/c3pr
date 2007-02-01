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
   String key = compId + "md2HardwareResourceListView";
   Boolean refreshContent = (Boolean)ResourceComponent.getPageAttribute(request, "refreshContent", Boolean.FALSE); %>
<oscache:cache key="<%=key%>" refresh="<%=refreshContent.booleanValue()%>">
<%
    List resourceList = (List)ResourceComponent.getPageAttribute(request, "resourceList", new ArrayList());
    String lastUpdated = (String)ResourceComponent.getPageAttribute(request, BaseResourcePage.LAST_UPDATED, "");
%>
    <h3>Host List <%=lastUpdated%></h3>
    <ui:table zebra="true" sortable="true" width="100%">
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text style="nostyle" value="Resource"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text style="nostyle" value="Hostname"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text style="nostyle" value="Platform"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text style="nostyle" value="CPUs"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text style="nostyle" value="System"/>
            </ui:tablecell>
<%--            <ui:tablecell>--%>
<%--                <ui:text style="nostyle" value="Shell"/>--%>
<%--            </ui:tablecell>--%>
        </ui:tablerow>
<%  Iterator hardwareResourceIterator = resourceList.iterator();
    while (hardwareResourceIterator.hasNext()) {
        HardwareResource resource = (HardwareResource)hardwareResourceIterator.next();
        String dn = resource.getDn();
        String hostName = resource.getHostName();
        String label = resource.getLabel();
        ResourceAttribute resourceAttribute = null;

        // Hardware Attribute = CPU Model
        StringBuffer hardwareAttribute = new StringBuffer();
        resourceAttribute = resource.getResourceAttribute(HardwareResource.COMPUTER_PLATFORM_ATTRIBUTE);
        if (resourceAttribute != null) {
            hardwareAttribute.append(resourceAttribute.getValue());
            hardwareAttribute.append(" ");
        }
        resourceAttribute = resource.getResourceAttribute(HardwareResource.CPU_MODEL_ATTRIBUTE);
        if (resourceAttribute != null) {
            hardwareAttribute.append(resourceAttribute.getValue());
        }
        // CPUs Attribute = CPU Count
        StringBuffer cpusAttribute = new StringBuffer();
        resourceAttribute = resource.getResourceAttribute(HardwareResource.CPU_COUNT_ATTRIBUTE);
        if (resourceAttribute != null) {
            cpusAttribute.append(resourceAttribute.getValue());
        }
        // OS Attribute = OS Name + OS Release
        StringBuffer osAttribute = new StringBuffer();
        resourceAttribute = resource.getResourceAttribute(HardwareResource.OS_NAME_ATTRIBUTE);
        if (resourceAttribute != null) {
            osAttribute.append(resourceAttribute.getValue());
            osAttribute.append(" ");
        }
        resourceAttribute = resource.getResourceAttribute(HardwareResource.OS_RELEASE_ATTRIBUTE);
        if (resourceAttribute != null) {
            osAttribute.append(resourceAttribute.getValue());
        }
        // Has ssh
        boolean hasSsh = (resource.hasChildResources(GridSshResourceType.INSTANCE));
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
                <ui:text value="<%=hardwareAttribute.toString()%>" style="nostyle"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=cpusAttribute.toString()%>" style="nostyle"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%=osAttribute.toString()%>" style="nostyle"/>
            </ui:tablecell>
<%--            <ui:tablecell>--%>
<%--                <% if (hasSsh) { %>--%>
<%--                <a href="/gridportlets/certmanagement/gsi-sshterm-<%=hostName%>.jnlp">Login</a>--%>
<%--                <% } else { %>--%>
<%--                &nbsp;--%>
<%--                <% } %>--%>
<%--            </ui:tablecell>--%>
        </ui:tablerow>
<%
    }
%>
    </ui:table>
</oscache:cache>
