<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:messagebox beanId="messageBox"/>

<ui:form>
    <ui:listbox beanId="allChannels"/>    <ui:actionsubmit action="showFeed" value="show news!"/>
</ui:form>


<ui:text beanId="feed"/>