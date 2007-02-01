<%@ page import="org.gridlab.gridsphere.services.ui.file.FileDownloadDialog,
                 org.gridlab.gridsphere.services.ui.ActionComponent"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<h3><ui:text value="File Editor" style="nostyle"/></h3>
<p><ui:text beanId="messageText"/></p>
<% Boolean saveAsFlag = (Boolean)ActionComponent.getPageAttribute(request, "saveAsFlag", Boolean.FALSE); %>
<ui:table width="400">
    <ui:tablerow>
        <ui:tablecell colspan="2">
                <ui:actionsubmit action="doOk" value="Save"/>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doCancel" value="Cancel"/>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell width="100">
            <b><ui:text style="nostyle" value="File Host:&nbsp;"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="fileHostField" size="63"/>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell width="100">
            <b><ui:text style="nostyle" value="File Path:&nbsp;"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="filePathField" size="63"/>
        </ui:tablecell>
    </ui:tablerow>
    <% if (saveAsFlag.booleanValue()) { %>
    <ui:tablerow>
        <ui:tablecell width="100">
            <b><ui:text style="nostyle" value="Save As:&nbsp;"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="fileNameField" size="63"/>
        </ui:tablecell>
    </ui:tablerow>
    <% } else { %>
    <ui:tablerow>
        <ui:tablecell width="100">
            <b><ui:text style="nostyle" value="File Name:&nbsp;"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="fileNameField" size="63"/>
        </ui:tablecell>
    </ui:tablerow>
    <% } %>
    <ui:tablerow>
        <ui:tablecell colspan="2">
            <ui:textarea beanId="fileTextArea" rows="80" cols="80"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
