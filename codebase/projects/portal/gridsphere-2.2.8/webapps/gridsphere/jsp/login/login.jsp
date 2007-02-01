<%@ page import="org.gridlab.gridsphere.portlet.impl.SportletProperties" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="certificate" class="java.lang.String" scope="request"/>
<jsp:useBean id="useSecureLogin" class="java.lang.String" scope="request"/>

<ui:form secure="<%= Boolean.valueOf(useSecureLogin).booleanValue() %>">
    <ui:messagebox beanId="msg"/>

    <% if (request.getAttribute("certificate") != null && ((String) request.getAttribute("certificate")).length() > 0)  { %>
    <ui:table>
            <ui:tablerow>
                <ui:tablecell width="160">
                    <ui:text key="LOGIN_CERTIFICATE"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell width="160">
                    <%= certificate %>
                </ui:tablecell>
            </ui:tablerow>
    </ui:table>
    <% } else { %>

    <ui:table>
        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text key="LOGIN_NAME"/>
            </ui:tablecell>
            <ui:tablecell width="60">
                <ui:text var="userkey" key="USER_NAME_BLANK"/>
                <input class="checkNotEmpty#" type="text" name="username" size="20" maxlength="50"/>
                <input type="hidden" name="username#checkNotEmpty" value="<%= userkey %>"/>
            </ui:tablecell>
            <ui:tablecell/>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text key="LOGIN_PASS"/>
            </ui:tablecell>
            <ui:tablecell width="60">
                <ui:text var="passkey" key="USER_PASSWORD_BLANK"/>
                <input class="checkNotEmpty#" type="password" name="password" size="20" maxlength="50"/>
                <input type="hidden" name="password#checkNotEmpty" value="<%= passkey %>"/>
            </ui:tablecell>
            <ui:tablecell/>
        </ui:tablerow>
    </ui:table>

    <% if (request.getAttribute("remUser") != null) { %>
    <p>
        <input type="checkbox" name="remlogin" value="yes"/><ui:text key="LOGIN_REMEMBER_ME"/>
    </p>
    <% } %>

    <% } %>

    <ui:table>
        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:actionsubmit action="<%= SportletProperties.LOGIN %>" key="LOGIN_ACTION">
                    <% if (request.getParameter("cid") != null) { %>
                    <ui:actionparam name="queryString" value="<%= request.getParameter("cid") %>"/>
                    <% } %>
                </ui:actionsubmit>
            </ui:tablecell>
            <ui:tablecell/>
        </ui:tablerow>
    </ui:table>

    <p>
        <% if (request.getAttribute("canUserCreateAcct") != null) { %>
        <ui:actionlink action="doNewUser" key="LOGIN_SIGNUP"/>
    </p>

    <p>
        <% } %>

        <% if ((request.getAttribute("dispPass") != null) && ((request.getAttribute("certificate") == null) || ((String) request.getAttribute("certificate")).length() == 0)) { %>
        <ui:actionlink action="displayForgotPassword" key="LOGIN_FORGOT_PASSWORD"/>
    </p>
    <% } %>

</ui:form>
