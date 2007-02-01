<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<%--<ui:messagebox beanId="messageBox"/>--%>
<ui:table>
    <ui:tablerow>
        <ui:tablecell width="100%">
            <ui:textarea beanId="jobStringArea" cols="80" rows="30"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
