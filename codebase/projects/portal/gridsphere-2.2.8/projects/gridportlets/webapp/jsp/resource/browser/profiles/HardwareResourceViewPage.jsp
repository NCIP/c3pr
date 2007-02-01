<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.services.resource.HardwareResource,
                 java.util.List,
                 org.gridlab.gridsphere.services.ui.ActionComponent,
                 org.gridlab.gridsphere.services.ui.resource.browser.ResourceComponent,
                 org.gridlab.gridsphere.portlet.impl.SportletProperties"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="/oscache" prefix="oscache" %>
<portlet:defineObjects/>
<% String compId = (String) request.getAttribute(SportletProperties.GP_COMPONENT_ID);
   String key = compId + "generalHardwareResourceView_" + (String)ResourceComponent.getPageAttribute(request, "resourceOid", "0");
   Boolean refreshContent = (Boolean)ResourceComponent.getPageAttribute(request, "refreshContent", Boolean.FALSE); %>
<oscache:cache key="<%=key%>" refresh="<%=refreshContent.booleanValue()%>">
<h3><ui:text style="nostyle" beanId="hostLabelText"/></h3>
<!-- RESOURCE ATTRIBUTES -->
<ui:table>

    <ui:tablerow>

        <ui:tablecell>
            <b><ui:text value="Hostname" style="nostyle"/></b>
        </ui:tablecell>
        <ui:tablecell>
            &nbsp;&nbsp;<ui:text beanId="hostNameText"/>
        </ui:tablecell>
        <ui:tablecell valign="top">
            &nbsp;&nbsp;-&nbsp;&nbsp;<ui:text beanId="hostDescriptionText"/>
        </ui:tablecell>

    </ui:tablerow>

</ui:table>

<br>

<ui:table>

    <ui:tablerow>

        <ui:tablecell width="100" valign="top">
            <ui:image beanId="hostImage1"/><br>
            <ui:text beanId="hostImage1Label"/>
        </ui:tablecell>

        <ui:tablecell valign="top">
            <ui:text beanId="hostHtmlText"/>
            <% String hostHtmlUrl = (String)ResourceComponent.getPageAttribute(request, "hostHtmlUrl");
               if (hostHtmlUrl != null) { %>
            <ui:include servletContext="<%=request.getSession().getServletContext()%>" page="<%=hostHtmlUrl%>"/>
            <% } %>
        </ui:tablecell>

    </ui:tablerow>

</ui:table>
</oscache:cache>
