<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<%--<ui:messagebox beanId="messageBox"/>--%>
<ui:table>
    <ui:tablerow>
        <ui:tablecell>
            <pre><ui:text beanId="jobStdoutText"/></pre>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <pre><ui:text beanId="jobStderrText"/></pre>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
