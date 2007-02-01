<%@ page import="org.gridlab.gridsphere.services.ui.file.FileDownloadDialog"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<h3><ui:text value="Rename File Or Directory" style="nostyle"/></h3>
<p><ui:text beanId="messageText"/></p>
<ui:table>
    <ui:tablerow>
        <ui:tablecell>
            <b><ui:text value="Current Host" style="nostyle"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="fileHostField" size="40"/>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <b><ui:text value="File To Rename" style="nostyle"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="filePathField" size="40"/>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <b><ui:text value="New Name" style="nostyle"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="fileNameField" size="40"/>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <ui:actionsubmit action="doOk" value="Apply"/>
            &nbsp;&nbsp;
            <ui:actionsubmit action="doCancel" value="Cancel"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
