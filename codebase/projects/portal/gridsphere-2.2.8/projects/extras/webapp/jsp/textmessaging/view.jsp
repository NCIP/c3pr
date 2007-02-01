<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:messagebox beanId="messagebox"/>

<ui:form>

    Message <br/><ui:textarea cols="40" rows="10" beanId="message"/> <br/>
    to <ui:listbox beanId="userlist"/> via <ui:listbox beanId="services"/>
    <ui:actionsubmit action="sendIM" value="Send"/>

</ui:form>