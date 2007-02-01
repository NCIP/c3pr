<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:image beanId="chart1"/>
<ui:form>
    <ui:actionsubmit action="createChart" value="Create Chart"/>
</ui:form>

