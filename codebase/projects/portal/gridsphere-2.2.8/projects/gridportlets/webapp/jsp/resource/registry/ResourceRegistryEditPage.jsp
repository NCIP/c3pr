<%@ page import="org.gridlab.gridsphere.services.ui.file.FileDownloadDialog"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
    <h3><ui:text style="nostyle" value="Resource Registry Edit"/></h3>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                    <ui:actionsubmit action="doSave" value="Save"/>
                    &nbsp;&nbsp;
                    <ui:actionsubmit action="doView" value="Cancel"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell valign="top">
                <b>Registry File</b>: <ui:text beanId="filePathText"/>
                &nbsp;&nbsp;
                <b>Last modified</b>: <ui:text beanId="lastModifiedText"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell valign="top">
                <ui:textarea beanId="resourceRegistryArea" rows="30" cols="80"/>
            </ui:tablecell>
<%--            <ui:tablecell valign="top">--%>
<%--                <ui:table>--%>
<%--                    <ui:tablerow>--%>
<%--                        <ui:tablecell colspan="2">--%>
<%--                            <h4><ui:text style="nostyle" value="File Info"/></h4>--%>
<%--                        </ui:tablecell>--%>
<%--                    </ui:tablerow>--%>
<%--                    <ui:tablerow>--%>
<%--                        <ui:tablecell width="100">--%>
<%--                            <ui:text style="bold" value="File Path"/>--%>
<%--                        </ui:tablecell>--%>
<%--                        <ui:tablecell>--%>
<%--                            <ui:text beanId="filePathText"/>--%>
<%--                        </ui:tablecell>--%>
<%--                    </ui:tablerow>--%>
<%--                    <ui:tablerow>--%>
<%--                        <ui:tablecell>--%>
<%--                            <ui:text style="bold" value="Last Modified"/>--%>
<%--                        </ui:tablecell>--%>
<%--                        <ui:tablecell>--%>
<%--                            <ui:text beanId="lastModifiedText"/>--%>
<%--                        </ui:tablecell>--%>
<%--                    </ui:tablerow>--%>
<%--                    <ui:tablerow>--%>
<%--                        <ui:tablecell>--%>
<%--                            <ui:text style="bold" value="Mapping File"/>--%>
<%--                        </ui:tablecell>--%>
<%--                        <ui:tablecell>--%>
<%--                            <ui:text beanId="mappingFilePathText"/>--%>
<%--                        </ui:tablecell>--%>
<%--                    </ui:tablerow>--%>
<%--                </ui:table>--%>
<%--            </ui:tablecell>--%>
        </ui:tablerow>
    </ui:table>
