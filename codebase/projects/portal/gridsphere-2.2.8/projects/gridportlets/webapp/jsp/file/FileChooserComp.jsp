<%@ page import="org.gridlab.gridsphere.services.ui.file.FileChooserComp,
                 org.gridlab.gridsphere.services.ui.file.FileChooserComp"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<% Boolean displayModeFlag = (Boolean)FileChooserComp.getPageAttribute(request, "displayModeFlag", Boolean.FALSE); %>
    <ui:hiddenfield beanId="currentHostField"/>
    <ui:hiddenfield beanId="currentPathField"/>
<ui:table width="400">
    <ui:tablerow>
        <ui:tablecell align="left" valign="top">
            <ui:group label="File Chooser">
<% if (displayModeFlag.booleanValue()) { %>
            <ui:table>
                <ui:tablerow>
                    <ui:tablecell>
                        <div style="font-weight:bold">
                        <ui:actionlink style="nostyle" action="doSetMode" value="Physical Files">
                            <ui:actionparam name="modeParam" value="PHYSICAL_BROWSER_MODE"/>
                        </ui:actionlink>
                        </div>
                    </ui:tablecell>
                    <ui:tablecell width="10">
                        &nbsp;
                    </ui:tablecell>
                    <ui:tablecell>
                        <div style="font-weight:bold">
                        <ui:actionlink style="nostyle" action="doSetMode" value="Logical Files">
                            <ui:actionparam name="modeParam" value="LOGICAL_BROWSER_MODE"/>
                        </ui:actionlink>
                        </div>
                    </ui:tablecell>
                </ui:tablerow>
            </ui:table>
            <br>
<% } %>
            <ui:table width="400">
                <ui:tablerow>
                    <ui:tablecell align="left" valign="top">
                        <ui:table>
                            <ui:tablerow>
                                <ui:tablecell valign="top">
                                    <ui:listbox beanId="fileHostList" size="14"/>
                                </ui:tablecell>
                            </ui:tablerow>
                        </ui:table>
                    </ui:tablecell>
                    <ui:tablecell align="left" valign="top">
                        <ui:table>
                            <ui:tablerow>
                                <ui:tablecell valign="top">
                                    <b><ui:text value="Directory" style="nostyle"/></b>
                                </ui:tablecell>
                                <ui:tablecell valign="top">
                                    <ui:actionsubmit action="doListFilesInDir" value="List"/>
                                </ui:tablecell>
                                <ui:tablecell valign="top">
                                    <ui:listbox beanId="fileDirList"/>
                                </ui:tablecell>
                            </ui:tablerow>
                            <ui:tablerow>
                                <ui:tablecell valign="top">
                                    <b><ui:text value="Contents" style="nostyle"/></b>
                                </ui:tablecell>
                                <ui:tablecell valign="top">
                                    <ui:actionsubmit action="doListFiles" value="List"/>
                                </ui:tablecell>
                                <ui:tablecell valign="top">
                                    <ui:listbox beanId="filePathList" size="10" />
                                </ui:tablecell>
                            </ui:tablerow>
                            <ui:tablerow>
                                <ui:tablecell valign="top">
                                    <b><ui:text value="Path" style="nostyle"/></b>
                                </ui:tablecell>
                                <ui:tablecell valign="top">
                                    <ui:actionsubmit action="doListFilesInPath" value="List"/>
                                </ui:tablecell>
                                <ui:tablecell valign="top">
                                    <ui:textfield beanId="filePathField" size="30"/>
                                </ui:tablecell>
                            </ui:tablerow>
                        </ui:table>
                    </ui:tablecell>
                </ui:tablerow>
                <ui:tablerow>
                    <ui:tablecell valign="top">
                        &nbsp;
                    </ui:tablecell>
                    <ui:tablecell colspan="2" valign="top">
                        <ui:actionsubmit action="doMakeDir" value="Make Dir"/>
                        &nbsp;
                        <ui:actionsubmit action="doUploadFile" value="Upload File"/>
                    </ui:tablecell>
                </ui:tablerow>
            </ui:table>
        </ui:group>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
