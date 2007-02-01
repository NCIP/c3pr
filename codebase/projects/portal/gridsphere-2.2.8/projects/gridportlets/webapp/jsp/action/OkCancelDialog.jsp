<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<ui:table>
    <ui:tablerow>
        <ui:tablecell>
            <ui:actionsubmit action="doOk" value="OK"/>
            &nbsp;&nbsp;
            <ui:actionsubmit action="doCancel" value="Cancel"/>
            &nbsp;&nbsp;
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
<p><ui:text beanId="messageText"/></p>
<ui:actioncomponent beanId="dialogBean"/>
