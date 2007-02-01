<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:messagebox beanId="messageBox"/>

<ui:form>
    <ui:listbox beanId="allChannels"/>    <ui:actionsubmit action="deleteFeed" value="deleteFeed"/>

    <br/>
    <ui:text value="Add new Feed"/><br/>
    <ui:text value="Name: "/><ui:textfield beanId="name"/>
    <ui:text value="RSS Url: "/><ui:textfield beanId="url"/>
    <ui:actionsubmit action="addFeed" value="add feed"/>
</ui:form>

