<%@ page import="org.gridlab.gridsphere.services.ui.file.FileDownloadDialog"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<%
    String fileName = (String)FileDownloadDialog.getPageAttribute(request, "fileName", "");
    String fileUrl = (String)FileDownloadDialog.getPageAttribute(request, "fileUrl", "");
%>
<ui:table width="400">
    <ui:tablerow>
        <ui:tablecell>
            <p>
                Click on the link below to begin downloading the file.
                <br>
            </p>
            <p>
                &nbsp;&nbsp;<a href="<%=fileUrl%>"><%=fileName%></a>
            </p>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <br>
            <ui:actionsubmit action="doOk" value="Ok"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
