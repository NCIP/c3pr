<%@ page import="org.gridlab.gridsphere.portlets.ActionComponentPortlet,
                 org.gridlab.gridsphere.services.ui.resource.browser.ResourceBrowser,
                 org.gridlab.gridsphere.services.resource.ResourceType,
                 org.gridlab.gridsphere.services.resource.HardwareResourceType,
                 org.gridlab.gridsphere.portlet.impl.SportletProperties,
                 org.gridlab.gridsphere.services.ui.resource.browser.ResourceComponent"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="/oscache" prefix="oscache" %>
<portlet:defineObjects/>
<% String compId = (String) request.getAttribute(SportletProperties.GP_COMPONENT_ID);
   String key = compId + "resourceBrowser";
   Boolean refreshContent = (Boolean)ResourceComponent.getPageAttribute(request, "refreshContent", Boolean.FALSE); %>
<oscache:cache key="<%=key%>" refresh="<%=refreshContent.booleanValue()%>">
<%
    String resourceDn = (String)ResourceBrowser.getPageAttribute(request, "resourceDn", "");
    ResourceType resourceType
            = (ResourceType)ResourceBrowser.getPageAttribute(request, "resourceType", HardwareResourceType.INSTANCE);
    String pageType = (String)ResourceBrowser.getPageAttribute(request, "pageType", "list");
    Integer rpListSize = (Integer)ResourceBrowser.getPageAttribute(request, "resourceProfileListSize", new Integer(1));
%>
<!-- Tab menu -->
<br>

<ui:actionpane menutype="actiontab">
    <ui:actionmenu>
        <ui:actionmenuitem selected="<%=resourceType.getID().equals("HardwareResource")%>">
            <ui:actionlink action="doSelectTab" value="Resources">
                <ui:actionparam name="resourceTypeID" value="HardwareResource"/>
            </ui:actionlink>
        </ui:actionmenuitem>
        <ui:actionmenuitem selected="<%=resourceType.getID().equals("ServiceResource")%>">
            <ui:actionlink action="doSelectTab" value="Services">
                <ui:actionparam name="resourceTypeID" value="ServiceResource"/>
            </ui:actionlink>
        </ui:actionmenuitem>
        <ui:actionmenuitem selected="<%=resourceType.getID().equals("JobQueue")%>">
            <ui:actionlink action="doSelectTab" value="Job Queues">
                <ui:actionparam name="resourceTypeID" value="JobQueue"/>
            </ui:actionlink>
        </ui:actionmenuitem>
        <ui:actionmenuitem selected="<%=resourceType.getID().equals("org.gridlab.gridsphere.services.job.Job")%>">
            <ui:actionlink action="doSelectTab" value="Jobs">
                <ui:actionparam name="resourceTypeID" value="org.gridlab.gridsphere.services.job.Job"/>
            </ui:actionlink>
        </ui:actionmenuitem>
        <ui:actionmenuitem selected="<%=resourceType.getID().equals("HardwareAccount")%>">
            <ui:actionlink action="doSelectTab" value="Accounts">
                <ui:actionparam name="resourceTypeID" value="HardwareAccount"/>
            </ui:actionlink>
        </ui:actionmenuitem>
    </ui:actionmenu>
    <ui:actionbody>
        <ui:table width="100%">
            <ui:tablerow>
                <ui:tablecell>
                <% if (pageType.equals("list")) { %>
                <ui:actionsubmit action="doResourceListViewPage" value="Refresh List"/>
                    <% if (!resourceType.getID().equals("HardwareResource")) {%>
                &nbsp;&nbsp;
                <ui:listbox beanId="hardwareResourceListBox"/>
                &nbsp;
                <ui:actionsubmit action="doSelectHardwareResource" value="Change Resource"/>
                    <% } %>
                <% } else { %>
                <ui:actionsubmit action="doResourceListViewPage" value="&lt;&lt; List">
                <ui:actionparam name="resourceDn" value="<%=resourceDn%>"/>
                </ui:actionsubmit>
                &nbsp;
                <ui:actionsubmit action="doResourceRefreshPage" value="Refresh View">
                    <ui:actionparam name="resourceDn" value="<%=resourceDn%>"/>
                </ui:actionsubmit>
                    <% if (resourceType.getID().equals("HardwareResource")) {%>
                &nbsp;&nbsp;
                <ui:listbox beanId="hardwareResourceListBox"/>
                &nbsp;
                <ui:actionsubmit action="doSelectHardwareResource" value="Change Resource"/>
                    <% } %>
                <%   } %>
                <%     if (rpListSize.intValue() > 1) { %>
                &nbsp;&nbsp;
                <ui:listbox beanId="resourceProfileListBox"/>
                &nbsp;
                <ui:actionsubmit action="doSelectResourceProfile" value="Change Profile">
                    <ui:actionparam name="resourceDn" value="<%=resourceDn%>"/>
                </ui:actionsubmit>
                <%     }  %>
                </ui:tablecell>
            </ui:tablerow>
        </ui:table>
        <ui:actioncomponent beanId="resourceProfileBean"/>
    </ui:actionbody>
</ui:actionpane>
</oscache:cache>
