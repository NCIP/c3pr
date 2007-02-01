<%@ page import="org.gridlab.gridsphere.services.ui.file.FileListComp,
                 org.gridlab.gridsphere.portlet.impl.SportletProperties,
                 javax.portlet.PortletSession,
                 javax.portlet.PortletURL"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%
Boolean showMakeDir = (Boolean)FileListComp.getPageAttribute(request, FileListComp.SHOW_MAKE_DIR_ACTION, Boolean.TRUE);
Boolean showRename = (Boolean)FileListComp.getPageAttribute(request, FileListComp.SHOW_RENAME_ACTION, Boolean.TRUE);
Boolean showDelete = (Boolean)FileListComp.getPageAttribute(request, FileListComp.SHOW_DELETE_ACTION, Boolean.TRUE);
Boolean showView = (Boolean)FileListComp.getPageAttribute(request, FileListComp.SHOW_VIEW_ACTION, Boolean.TRUE);
Boolean showUpload = (Boolean)FileListComp.getPageAttribute(request, FileListComp.SHOW_UPLOAD_ACTION, Boolean.TRUE);
Boolean showDownload = (Boolean)FileListComp.getPageAttribute(request, FileListComp.SHOW_DOWNLOAD_ACTION, Boolean.TRUE);
%>
<portlet:defineObjects/>
<ui:hiddenfield beanId="currentHostField"/>
<ui:hiddenfield beanId="currentPathField"/>
<ui:table>
    <ui:tablerow>
        <ui:tablecell align="left" valign="top">
            <ui:table>
                <ui:tablerow>
                    <ui:tablecell valign="top">
                        <ui:listbox beanId="fileHostList" size="15"/>
                    </ui:tablecell>
                </ui:tablerow>
            </ui:table>
        </ui:tablecell>
        <ui:tablecell align="left" valign="top">
            <ui:table>
                <ui:tablerow>
                   <ui:tablecell valign="top">
                        <ui:actionsubmit action="doListFilesInDir" value="<< List"/>
                    </ui:tablecell>
                    <ui:tablecell  width="200" valign="top">
                        <ui:listbox beanId="fileDirList"/>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell valign="top">
                        <ui:actionsubmit action="doListFiles" value="List >>"/>
                    </ui:tablecell>
                    <ui:tablecell valign="top">
                        <ui:listbox beanId="filePathList" size="10" />
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell valign="top">
                        <ui:actionsubmit action="doListFilesInPath" value="Path"/>
                    </ui:tablecell>
                    <ui:tablecell valign="top">
                        <ui:textfield beanId="filePathField" size="30"/>
                    </ui:tablecell>
                </ui:tablerow>
            </ui:table>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell colspan="2" valign="top">
<% if(showMakeDir.booleanValue()) { %>
            <ui:actionsubmit action="doMakeDir" value="Make Dir"/>
            &nbsp;
<% } %>
<% if(showRename.booleanValue()) { %>
            <ui:actionsubmit action="doRenameFile" value="Rename"/>
            &nbsp;
<% } %>
<% if(showDelete.booleanValue()) {  %>
            <ui:actionsubmit action="doDeleteFiles" value="Delete"/>
            &nbsp;
<% } %>
<% if(showView.booleanValue()) { %>
            <ui:actionsubmit action="doEditFile" value="View"/>
            &nbsp;
<% } %>
<% if(showUpload.booleanValue()) { %>
            <ui:actionsubmit action="doUploadFile" value="Upload"/>
            &nbsp;
<% } %>
<% if(showDownload.booleanValue()) { %>
            <ui:actionsubmit action="doDownloadFile" value="Download"/>
<% } %>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
