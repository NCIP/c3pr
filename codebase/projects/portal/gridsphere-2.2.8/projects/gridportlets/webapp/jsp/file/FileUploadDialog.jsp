<%@ page import="org.gridlab.gridsphere.services.ui.ActionComponent"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<h3><ui:text value="Upload File" style="nostyle"/></h3>
<p><ui:text beanId="messageText"/></p>
<% Boolean saveAsFlag = (Boolean)ActionComponent.getPageAttribute(request, "saveAsFlag", Boolean.FALSE); %>
<ui:table>
    <ui:tablerow>
        <ui:tablecell>
            <b><ui:text value="Destination Host" style="nostyle"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="fileHostField" size="40"/>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell>
            <b><ui:text value="Destination Path" style="nostyle"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="filePathField" size="40"/>
        </ui:tablecell>
    </ui:tablerow>
    <% if (saveAsFlag.booleanValue()) { %>
    <ui:tablerow>
        <ui:tablecell width="100">
            <b><ui:text style="nostyle" value="Save As:&nbsp;"/></b>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="fileNameField" size="40"/>
        </ui:tablecell>
    </ui:tablerow>
    <% } %>
    <ui:tablerow>
        <ui:tablecell colspan="2">
            <ui:fileform action="doOk">
                    <ui:table>
                        <ui:tablerow>
                            <ui:tablecell>
                                 <ui:fileinput beanId="fileUploadBean" size="40" maxlength="40"/>
                                &nbsp;&nbsp;
                                <ui:actionsubmit action="doOk" value="Upload"/>
                            </ui:tablecell>
                        </ui:tablerow>
                    </ui:table>
            </ui:fileform>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell colspan="2">
            <ui:form>
                <ui:actionsubmit action="doCancel" value="Cancel"/>
                &nbsp;&nbsp;
            </ui:form>
        </ui:tablecell>
    </ui:tablerow>
</ui:table>
