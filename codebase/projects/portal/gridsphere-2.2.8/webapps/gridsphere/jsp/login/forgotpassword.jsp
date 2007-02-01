<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:messagebox beanId="msg"/>

<ui:form>
    <ui:messagebox key="LOGIN_FORGOT_TEXT"/>

    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="LOGIN_REQUEST_EMAIL"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                <ui:textfield beanId="emailTF" size="25" maxlength="40"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>

    <ui:table>
        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:actionsubmit action="notifyUser" key="OK"/>
            </ui:tablecell>
            <ui:tablecell width="100">
                <ui:actionsubmit action="doViewUser" key="CANCEL"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
</ui:form>