<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter" %>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.acegisecurity.AuthenticationException" %>


<html>
<meta name="navigationBarVisibility" content="hidden">

<head>
    <script>
        function login(role){
            document.loginForm.j_username.value=role;
            document.loginForm.j_password.value=role;

        }
    </script>
</head>

<body>
<!-- SUB NAV STARTS HERE -->
<form method="post" name="loginForm" action="<c:url value="/j_acegi_security_check"/>">
    <div id="subNav" style="float:left;width:99%;">
        <div style="width:70%;float:right;">

            <div>
                <c:if test="${not empty param.login_error}">
                    <span class="red">* Invalid Login. Please Try Again.</span>
                </c:if>
            </div>

            <div style="float:left;margin-bottom:10px;">
                <div>
                    Username:
                    <input type="text" name="j_username"
                            <c:if test="${not empty param.login_error}">
                                value="<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>"
                            </c:if>
                            />
                </div>

                <div align="center">
                    Password:
                    <input type="password" name="j_password"/>
                </div>
                &nbsp;

                <div align="center">

                    <input type="image"
                           name="_target5" src="<tags:imageUrl name="b-submit.gif"/>"
                           alt="submit">
                </div>
            </div>

            <div style="float:left;margin-left:30px;margin-top:1px;">
                <div>
                    <a href="javascript:login('c3pr_admin')">Log in as Admin</a>
                </div>

                <div>
                    <a href="javascript:login('c3pr_registrar')">Log in as Registrar</a>
                </div>

                <div>
                    <a href="javascript:login('c3pr_study_coordinator')">Log in as Study Coordinator</a>
                </div>
            </div>



        </div>


    </div>




</form>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>
