<%@ page import="org.gridlab.gridsphere.portlets.ActionComponentPortlet"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<% String beanId = ActionComponentPortlet.getActionComponentBeanId(request); %>
<% boolean displayForm = ActionComponentPortlet.displayActionForm(request); %>
<% if (displayForm) { %>
<ui:form>
    <ui:actioncomponent beanId="<%=beanId%>"/>
</ui:form>
<% } else { %>
    <ui:actioncomponent beanId="<%=beanId%>"/>
<% } %>
