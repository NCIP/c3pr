<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter" %>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.acegisecurity.AuthenticationException" %>


<html>
<meta name="navigationBarVisibility" content="hidden">

<head>
    <script>
        function navRollOver(obj, state) {
        document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
        }
    </script>
</head>

<body>
<!-- SUB NAV STARTS HERE -->
<form method="post" name="loginForm" action="<c:url value="j_acegi_security_check"/>">

    <c:if test="${not empty param.login_error}">
      	<tr>
				<td align="center"><span class="red">* Invalid Login. Please Try Again.</span></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
    </c:if>

    <table width="100%" border="0" cellspacing="0" cellpadding="0"
           id="subNav">
        <tr valign="middle">
            <td>
                <table width="20%" align="center" border="0" cellspacing="0"
                       cellpadding="0">
                    <tr>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="label">Username:</td>
                        <td>
                            <input type="text" name="j_username"
                                    <c:if test="${not empty param.login_error}">
                                        value="<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>"
                                    </c:if>
                                    />

                        </td>
                    </tr>
                    <tr>
                        <td class="label">Password:</td>
                        <td><input type="password" name="j_password"/></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan=2 align="center"><input type="image"
                                                            name="_target5" src="/c3pr/images/b-submit.gif"
                                                            alt="submit"></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                    </tr>
                </table>
            </td>
        </tr>

    </table>
</form>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
